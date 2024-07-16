package com.rabbitq.models.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.rabbitq.annotations.InfoGatherInterfaceImplementation;
import com.rabbitq.dao.OscsVulInfoMapper;
import com.rabbitq.entity.OscsVulInfo;
import com.rabbitq.models.InfoGatherInterface;
import com.rabbitq.utils.DingTalkRobot;
import com.rabbitq.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.List;

import static com.rabbitq.utils.GlobalConfig.init;

/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-07-09
 * @Description: oscs漏洞情报
 * @Version: 1.0
 */

@InfoGatherInterfaceImplementation
public class OscsInfoGatherImpl implements InfoGatherInterface {

    @Override
    public void getRepos() {
        String strOscsReportURL = "https://www.oscs1024.com/oscs/v1/intelligence/list";
        String detailURL = "https://www.oscs1024.com/oscs/v1/vdb/info";
        String result1 = HttpRequest.post(strOscsReportURL).execute().body();
        JSONObject jsonObjectAPIResult = JSONObject.parseObject(result1);
        JSONArray jsonArray =  jsonObjectAPIResult.getJSONObject("data").getJSONArray("data");
        SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObjectVulInfo = jsonArray.getJSONObject(i);
            String title = jsonObjectVulInfo.getString("title");

            OscsVulInfoMapper oscsVulInfoMapper = session.getMapper(OscsVulInfoMapper.class);
            OscsVulInfo oscsVulInfo = oscsVulInfoMapper.selectByTitle(title);
            if (oscsVulInfo != null) {
                continue;
            } else {
                oscsVulInfo = new OscsVulInfo();
            }
            oscsVulInfo.setTitle(title);
            oscsVulInfo.setPublicTime(jsonObjectVulInfo.getString("public_time"));

            String mps = jsonObjectVulInfo.getString("mps");
            String strParam = "{\"vuln_no\":\"%s\"}";
            String resultDetail = HttpRequest.post(detailURL).body(String.format(strParam, mps)).execute().body();
            JSONObject jsonObjectVulInfoDetail = JSONObject.parseObject(resultDetail).getJSONArray("data").getJSONObject(0);
            oscsVulInfo.setSuggest(jsonObjectVulInfoDetail.getString("soulution_data"));
            JSONArray jsonArrayReference = jsonObjectVulInfoDetail.getJSONArray("references");
            List<String> listReferences = new ArrayList<>();
            for (int j = 0; j < jsonArrayReference.size(); j++) {
                listReferences.add(jsonArrayReference.getJSONObject(j).getString("url"));
            }
            oscsVulInfo.setReference(String.valueOf(listReferences));
            oscsVulInfo.setLevel(jsonObjectVulInfoDetail.getString("level"));
            oscsVulInfo.setVulnType(jsonObjectVulInfoDetail.getString("vuln_type"));
            oscsVulInfo.setCnvdId(jsonObjectVulInfoDetail.getString("cnvd_id"));
            oscsVulInfo.setCveId(jsonObjectVulInfoDetail.getString("cve_id"));
            oscsVulInfo.setDescription(jsonObjectVulInfoDetail.getString("description"));
            oscsVulInfoMapper.insert(oscsVulInfo);

            if (init) {
                String content = DingTalkRobot.buildOscsMarkdownText(oscsVulInfo);
                DingTalkRobot.sendMarkdownMessage(content);
            }
        }
        session.commit();
        session.close();
    }
}
