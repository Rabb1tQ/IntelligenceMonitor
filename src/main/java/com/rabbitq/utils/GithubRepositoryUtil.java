package com.rabbitq.utils;

import com.alibaba.fastjson2.JSONObject;
import com.rabbitq.dao.RepositoryMapper;
import com.rabbitq.entity.Repository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.time.ZonedDateTime;

import static com.rabbitq.utils.GlobalConfig.init;

/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-06-27
 * @Description: 仓库处理工具类
 * @Version: 1.0
 */


public class GithubRepositoryUtil {

    public static void getRepositoryInfo(SqlSessionFactory sqlSessionFactory, String strRepoFullName, JSONObject jsonObject) {
        SqlSession session = sqlSessionFactory.openSession();
        RepositoryMapper gitHubToolMapper = session.getMapper(RepositoryMapper.class);
        Repository repository = gitHubToolMapper.selectByRepoName(strRepoFullName);
        session.commit();
        if (repository != null) {
            ZonedDateTime timeLocalDate = ZonedDateTime.parse(repository.getPushedAt());
            ZonedDateTime timeRemoteDate = ZonedDateTime.parse(String.valueOf(jsonObject.get("pushed_at")));
            if (timeRemoteDate.isAfter(timeLocalDate)) {
                repository.setPushedAt(String.valueOf(jsonObject.get("pushed_at")));
                gitHubToolMapper.update(repository);
                session.commit();
                String[] arrRepoFullName=strRepoFullName.split("/");
                String content=DingTalkRobot.buildGithubMarkdownText(arrRepoFullName[0],arrRepoFullName[1],String.valueOf(jsonObject.get("description")),"https://github/"+strRepoFullName);
                DingTalkRobot.sendMarkdownMessage(content);
            } else {
                System.out.println("数据库中已存在仓库：" + strRepoFullName);
            }
        } else {
            String[] arrRepoFullName=strRepoFullName.split("/");
            repository = new Repository();
            repository.setPushedAt(String.valueOf(jsonObject.get("pushed_at")));
            repository.setRepoName(strRepoFullName);
            repository.setDescription(String.valueOf(jsonObject.get("description")));
            gitHubToolMapper.insert(repository);
            session.commit();
            if(init){
                String content=DingTalkRobot.buildGithubMarkdownText(arrRepoFullName[0],arrRepoFullName[1],String.valueOf(jsonObject.get("description")),"https://github/"+strRepoFullName);
                DingTalkRobot.sendMarkdownMessage(content);
            }

        }
        session.close();

    }
}