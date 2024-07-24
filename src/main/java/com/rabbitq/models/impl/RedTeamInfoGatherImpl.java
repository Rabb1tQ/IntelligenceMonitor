package com.rabbitq.models.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONObject;
import com.rabbitq.annotations.InfoGatherInterfaceImplementation;
import com.rabbitq.models.InfoGatherInterface;
import com.rabbitq.utils.GlobalConfig;
import com.rabbitq.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

import static com.rabbitq.utils.GithubRepositoryUtil.getRepositoryInfo;

/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-06-27
 * @Description: 红队工具类
 * @Version: 1.0
 */

@InfoGatherInterfaceImplementation
public class RedTeamInfoGatherImpl implements InfoGatherInterface {



    @Override
    public void getRepos() {
        String strReposLink = "https://api.github.com/repos/%s";
        List<String> listRepository;
        try {

            listRepository = (List<String>) GlobalConfig.globalConfig.get("tools_list");
        } catch (Exception e) {
            return;
        }
        SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
        for (String strRepoFullName : listRepository) {
            String strAPI = String.format(strReposLink, strRepoFullName);
            String result1;
            String strGithubToken=String.valueOf(GlobalConfig.globalConfig.get("github_token"));
            if (strGithubToken.isEmpty()){
                result1 = HttpRequest.get(strAPI).execute().body();
            }else {
                result1 = HttpRequest.get(strAPI).header("Authorization","token "+strGithubToken).execute().body();
            }
            JSONObject jsonObject = JSONObject.parseObject(result1);

            getRepositoryInfo(sqlSessionFactory, strRepoFullName, jsonObject);

        }
    }

}
