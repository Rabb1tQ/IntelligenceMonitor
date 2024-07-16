package com.rabbitq.models.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.rabbitq.annotations.InfoGatherInterfaceImplementation;
import com.rabbitq.dao.TiVulInfoMapper;
import com.rabbitq.entity.TiVulInfo;
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
 * @CreateTime: 2024-07-16
 * @Description: 奇安信威胁情报
 * @Version: 1.0
 */


@InfoGatherInterfaceImplementation
public class TiInfoGatherImpl  implements InfoGatherInterface {
    @Override
    public void getRepos() {
        String detailURL="https://ti.qianxin.com/alpha-api/v2/vuln/one-day";
        String resultDetail= HttpRequest.post(detailURL).execute().body();
        JSONObject jsonObjectAPIResult = JSONObject.parseObject(resultDetail);
        JSONArray jsonArray = jsonObjectAPIResult.getJSONObject("data").getJSONArray("vuln_add");
        SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObjectVulInfo = jsonArray.getJSONObject(i);
            String vuln_name = jsonObjectVulInfo.getString("vuln_name");
            TiVulInfoMapper tiVulInfoMapper = session.getMapper(TiVulInfoMapper.class);
            TiVulInfo tiVulInfo = tiVulInfoMapper.selectByVulName(vuln_name);
            if (tiVulInfo != null) {
                continue;
            } else {
                tiVulInfo = new TiVulInfo();
            }
            tiVulInfo.setVulName(vuln_name);
            tiVulInfo.setVulType(jsonObjectVulInfo.getString("vuln_type"));
            tiVulInfo.setPublishTime(jsonObjectVulInfo.getString("publish_time"));
            tiVulInfo.setDescription(jsonObjectVulInfo.getString("description"));
            tiVulInfo.setRatingLevel(jsonObjectVulInfo.getString("rating_level"));
            tiVulInfo.setCveId(jsonObjectVulInfo.getString("cve_code"));
            tiVulInfo.setCnvdId(jsonObjectVulInfo.getString("cnvd_id"));

            JSONArray jsonArrayTags = jsonObjectVulInfo.getJSONArray("tag");
            List<String> tags = new ArrayList<>();
            for (int j = 0; j < jsonArrayTags.size(); j++) {
                tags.add(jsonArrayTags.getJSONObject(j).getString("name"));
            }
            tiVulInfo.setTags(String.valueOf(tags));
            tiVulInfoMapper.insert(tiVulInfo);

            if (init) {
                String content = DingTalkRobot.buildTiMarkdownText(tiVulInfo);
                DingTalkRobot.sendMarkdownMessage(content);
            }
        }
        session.commit();
        session.close();
    }
}
