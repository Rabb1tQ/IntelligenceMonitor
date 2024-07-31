package com.rabbitq.models.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.rabbitq.annotations.InfoGatherInterfaceImplementation;
import com.rabbitq.dao.ThreatbookVulInfoMapper;
import com.rabbitq.entity.ThreatbookVulInfo;
import com.rabbitq.models.InfoGatherInterface;
import com.rabbitq.utils.DingTalkRobot;
import com.rabbitq.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.List;

import static com.rabbitq.utils.GlobalConfig.init;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-07-18
 * @Description: 微步漏洞抓取
 * @Version: 1.0
 */

@InfoGatherInterfaceImplementation
public class ThreatbookInfoGatherImpl implements InfoGatherInterface {
    private static final Logger log = LogManager.getLogger(ThreatbookInfoGatherImpl.class);
    @Override
    public void getRepos() {
        String strInfoLinkURL = "https://x.threatbook.com/v5/node/vul_module/homePage";
        String result1 = HttpRequest.get(strInfoLinkURL)
                .execute().body();
        JSONArray jsonArrayAPIResult = JSONObject.parseObject(result1).getJSONObject("data").getJSONArray("highrisk");
        SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        for (int i = 0; i < jsonArrayAPIResult.size(); i++) {
            JSONObject jsonObjectVulInfo = jsonArrayAPIResult.getJSONObject(i);
            ThreatbookVulInfoMapper threatbookVulInfoMapper= session.getMapper(ThreatbookVulInfoMapper.class);
            String vulnName = jsonObjectVulInfo.getString("vuln_name_zh");
            ThreatbookVulInfo threatbookVulInfo = threatbookVulInfoMapper.selectByVulName(vulnName);
            if (threatbookVulInfo != null) {
                continue;
            } else {
                threatbookVulInfo = new ThreatbookVulInfo();
            }
            List<String> tags=new ArrayList<>();
            boolean pocExist= (boolean) jsonObjectVulInfo.get("pocExist");

            if(pocExist){
                tags.add("POC存在");
            }

            boolean solution= (boolean) jsonObjectVulInfo.get("solution");
            if(solution){
                tags.add("有修复方案");
            }
            boolean premium= (boolean) jsonObjectVulInfo.get("premium");
            if(premium){
                tags.add("有漏洞分析");
            }
            if(tags.isEmpty()){
                tags.add("无");
            }
            threatbookVulInfo.setTags(tags.toString());
            threatbookVulInfo.setVulnUpdateTime(jsonObjectVulInfo.getString("vuln_publish_time"));
            threatbookVulInfo.setRiskLevel(jsonObjectVulInfo.getString("riskLevel"));
            List<String> listAffects= (List<String>) jsonObjectVulInfo.get("affects");
            threatbookVulInfo.setAffects(listAffects.toString());

            threatbookVulInfoMapper.insert(threatbookVulInfo);
            log.info("新增情报："+vulnName);

            if (init) {
                String content = DingTalkRobot.buildThreatbookMarkdownText(threatbookVulInfo);
                DingTalkRobot.sendMarkdownMessage(content);
            }
        }
        session.commit();
        session.close();
    }

}
