package com.rabbitq.utils;

import com.rabbitq.entity.AvdEntity;
import org.junit.jupiter.api.Test;

import java.util.Map;



class DingTalkRobotTest {

    @Test
    void buildAvdMarkdownText() {
        Map<String, Object> config = GlobalConfig.globalConfig;
        AvdEntity avdEntity = new AvdEntity();
        avdEntity.setVulName("泛微OA sql注入");
        avdEntity.setCveNumber("cve-xxxx-xxxx");
        avdEntity.setExpStatus("POC已公开");
        avdEntity.setLevel("严重");
        avdEntity.setVulDate("2024-07-06");
        avdEntity.setDescription("csacscsacscascsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        avdEntity.setVulType("sql注入");
        avdEntity.setSuggest("csacsac");
        avdEntity.setReference("https://www.baidu.com");
        String content= DingTalkRobot.buildAvdMarkdownText(avdEntity);
        DingTalkRobot.sendMarkdownMessage(content);
    }

}