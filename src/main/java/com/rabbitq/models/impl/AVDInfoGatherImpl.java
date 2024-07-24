package com.rabbitq.models.impl;

import com.rabbitq.annotations.InfoGatherInterfaceImplementation;
import com.rabbitq.dao.AvdMapper;
import com.rabbitq.entity.AvdEntity;
import com.rabbitq.job.ThreatIntelligenceJob;
import com.rabbitq.models.InfoGatherInterface;
import com.rabbitq.utils.DingTalkRobot;
import com.rabbitq.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;

/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-07-05
 * @Description: 阿里云漏洞库爬取
 * @Version: 1.0
 */

@InfoGatherInterfaceImplementation
public class AVDInfoGatherImpl  implements InfoGatherInterface {
    private static final Logger log = LogManager.getLogger(AVDInfoGatherImpl.class);
    @Override
    public void getRepos() {
        String strInfoLinkURL="https://avd.aliyun.com/high-risk/list";
        String strWebSiteURL="https://avd.aliyun.com";
        try {
            Document document = Jsoup.connect(strInfoLinkURL).get();
            Elements trAllOfVul=document.select("tbody").select("tr");
            SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
            for (Element trOneOfVul : trAllOfVul) {
                Elements tdDoc=trOneOfVul.select("td");
                String vulName=tdDoc.get(1).text();

                SqlSession session = sqlSessionFactory.openSession();
                AvdMapper avdMapper= session.getMapper(AvdMapper.class);
                AvdEntity avd=avdMapper.selectByVulName(vulName);
                if (avd != null) {
                    continue;
                }
                else {
                    avd=new AvdEntity();
                }
                avd.setVulName(vulName);

                avd.setVulType(tdDoc.get(2).select("button").attr("title"));
                avd.setVulDate(tdDoc.get(3).text());
                avd.setCveNumber(tdDoc.get(4).select("button").get(0).attr("title"));
                avd.setExpStatus(tdDoc.get(4).select("button").get(1).attr("title"));

                String strHref=strWebSiteURL+tdDoc.get(0).select("a").attr("href");
                Document docDetial=Jsoup.connect(strHref).get();
                Elements elmDetial=docDetial.select(".rounded").select("div.text-detail");
                avd.setDescription(elmDetial.get(0).text());
                avd.setSuggest(elmDetial.get(1).text());
                String reference= elmDetial.get(2).text().replace("参考链接","").replace(" ","</br>");
                avd.setReference(reference);
                avd.setLevel(docDetial.select(".badge").get(0).text());
                avdMapper.insert(avd);
                session.commit();
                session.close();
                log.info("新增情报："+vulName);
                String content= DingTalkRobot.buildAvdMarkdownText(avd);
                DingTalkRobot.sendMarkdownMessage(content);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
