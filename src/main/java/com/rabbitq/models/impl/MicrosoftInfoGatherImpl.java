package com.rabbitq.models.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.rabbitq.annotations.InfoGatherInterfaceImplementation;
import com.rabbitq.dao.CveMonitorMsMapper;
import com.rabbitq.entity.CveMonitorMs;
import com.rabbitq.models.InfoGatherInterface;
import com.rabbitq.utils.DingTalkRobot;
import com.rabbitq.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import static com.rabbitq.utils.GlobalConfig.init;

/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-06-30
 * @Description: 微软漏洞监控
 * @Version: 1.0
 */

@InfoGatherInterfaceImplementation
public class MicrosoftInfoGatherImpl implements InfoGatherInterface {
    @Override
    public void getRepos() {
        String strMsReportURL = "https://api.msrc.microsoft.com/sug/v2.0/zh-CN/vulnerability";
        String result1 = HttpRequest.get(strMsReportURL).execute().body();
        JSONObject jsonObjectAPIResult = JSONObject.parseObject(result1);
        JSONArray jsonArray = jsonObjectAPIResult.getJSONArray("value");
        SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
        for (int i = 0; i < 100; i++) {
            JSONObject jobjOneVul = jsonArray.getJSONObject(i);
            String strCVENumber = jobjOneVul.getString("cveNumber");
            SqlSession session = sqlSessionFactory.openSession();
            CveMonitorMsMapper cveMonitorMsMapperMapper = session.getMapper(CveMonitorMsMapper.class);
            CveMonitorMs cveMonitorMs = cveMonitorMsMapperMapper.selectByCveNumber(strCVENumber);
            session.commit();
            if (cveMonitorMs == null) {
                cveMonitorMs = new CveMonitorMs();
                String cveNumber = String.valueOf(jobjOneVul.get("cveNumber"));
                String releaseDate = String.valueOf(jobjOneVul.get("releaseDate"));
                String tag= String.valueOf(jobjOneVul.get("tag"));
                String mitreUrl= String.valueOf(jobjOneVul.get("mitreUrl"));
                cveMonitorMs.setCveNumber(cveNumber);
                cveMonitorMs.setReleaseDate(releaseDate);
                cveMonitorMs.setTag(tag);
                cveMonitorMs.setMitreUrl(mitreUrl);
                cveMonitorMsMapperMapper.insert(cveMonitorMs);
                session.commit();
                if (init) {
                    String content = DingTalkRobot.buildMSVulMarkdownText(cveNumber, releaseDate, tag, mitreUrl);
                    DingTalkRobot.sendMarkdownMessage(content);
                }
            }
            session.close();
        }

    }
}
