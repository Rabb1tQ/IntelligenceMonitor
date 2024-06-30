package com.rabbitq.models.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONArray;
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
 * @CreateTime: 2024-06-20
 * @Description: 获取红队工具仓库
 * @Version: 1.0
 */

@InfoGatherInterfaceImplementation
public class UserInfoGatherImpl implements InfoGatherInterface {


    @Override
    public void getRepos() {
        String strUserReposLink = "https://api.github.com/users/%s/repos?sort=updated&order=desc";
        List<String> listUser;
        try {
            listUser= (List<String>) GlobalConfig.globalConfig.get("user_list");
        }
        catch (Exception e) {
            return;
        }

        SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
        for (String s : listUser) {
            String strAPI = String.format(strUserReposLink, s);
            String result1 = HttpRequest.get(strAPI).execute().body();
            JSONArray jsonArray = JSONArray.parseArray(result1);
            for (Object o : jsonArray) {
                try {
                    JSONObject jsonObject = (JSONObject) o;
                    String strRepoFullName = String.valueOf(jsonObject.get("full_name"));
                    getRepositoryInfo(sqlSessionFactory, strRepoFullName, jsonObject);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
    }




}
