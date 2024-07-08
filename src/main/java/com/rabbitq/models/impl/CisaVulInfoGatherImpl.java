package com.rabbitq.models.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONObject;
import com.rabbitq.annotations.InfoGatherInterfaceImplementation;
import com.rabbitq.dao.CisaVulInfoMapper;
import com.rabbitq.entity.CisaVulInfo;
import com.rabbitq.models.InfoGatherInterface;
import com.rabbitq.utils.DingTalkRobot;
import com.rabbitq.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-07-08
 * @Description: Cisa已知的被利用的安全漏洞的信息
 * @Version: 1.0
 */


@InfoGatherInterfaceImplementation
public class CisaVulInfoGatherImpl  implements InfoGatherInterface {

    @Override
    public void getRepos() {
        String strCisaReportURL = "https://www.cisa.gov/sites/default/files/feeds/known_exploited_vulnerabilities.json";
        String result1 = HttpRequest.get(strCisaReportURL).execute().body();
        JSONObject jsonObjectAPIResult = JSONObject.parseObject(result1);
        List<JSONObject> vulnerabilities = jsonObjectAPIResult.getJSONArray("vulnerabilities").toJavaList(JSONObject.class);

        // 对漏洞信息进行排序
        vulnerabilities.sort((v1, v2) -> v2.getString("dateAdded").compareTo(v1.getString("dateAdded")));
        int itemLimit = Math.min(20, vulnerabilities.size());
        SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
        for (int i = 0; i < itemLimit; i++) {
            JSONObject vulnerability = vulnerabilities.get(i);
            String cve_number= vulnerability.getString("cveID");
            SqlSession session = sqlSessionFactory.openSession();
            CisaVulInfoMapper cisaVulInfoMapper= session.getMapper(CisaVulInfoMapper.class);
            CisaVulInfo cisaVulInfo=cisaVulInfoMapper.selectByCveNumber(cve_number);
            if (cisaVulInfo != null) {
                session.commit();
                session.close();
                break;
            }
            else {
                cisaVulInfo=new CisaVulInfo();
            }
            cisaVulInfo.setCveNumber(cve_number);
            cisaVulInfo.setDescription(vulnerability.getString("shortDescription"));
            cisaVulInfo.setSuggest(vulnerability.getString("requiredAction"));
            cisaVulInfo.setProduct(vulnerability.getString("product"));
            cisaVulInfo.setVendor(vulnerability.getString("vendorProject"));
            cisaVulInfo.setReference(vulnerability.getString("notes"));
            cisaVulInfo.setDateAdded(vulnerability.getString("dateAdded"));
            cisaVulInfo.setVulnerabilityName(vulnerability.getString("vulnerabilityName"));
            cisaVulInfoMapper.insert(cisaVulInfo);
            session.commit();
            session.close();
            String content= DingTalkRobot.buildCisaMarkdownText(cisaVulInfo);
            DingTalkRobot.sendMarkdownMessage(content);
        }
    }
}
