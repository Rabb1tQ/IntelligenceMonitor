package com.rabbitq.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.rabbitq.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.Map;

public class DingTalkRobot {

    private static final Logger log = LoggerFactory.getLogger(DingTalkRobot.class);

    public static String buildGithubMarkdownText(String userName, String repositoryName, String description, String repositoryUrl) {
        StringBuilder text = new StringBuilder();
        text.append("# github仓库监控：").append("\n\n");
        text.append("- **用户名**: ").append(userName).append(System.lineSeparator());
        text.append("- **仓库名称**: ").append(repositoryName).append(System.lineSeparator());
        text.append("- **描述**: ").append(description).append(System.lineSeparator());
        text.append("- **仓库地址**: [").append(repositoryName).append("](").append(repositoryUrl).append(")");
        return text.toString();
    }

    public static String buildThreatbookMarkdownText(ThreatbookVulInfo threatbookVulInfo) {
        StringBuilder text = new StringBuilder();

        text.append("# ").append(threatbookVulInfo.getVulnName()).append("</br></br>\n");
        text.append("* 漏洞标签：").append(threatbookVulInfo.getTags()).append(System.lineSeparator());
        text.append("* 披露日期：").append(threatbookVulInfo.getVulnUpdateTime()).append(System.lineSeparator());
        text.append("* 风险等级：").append(threatbookVulInfo.getRiskLevel()).append(System.lineSeparator());
        text.append("* 影响产品：").append(threatbookVulInfo.getAffects()).append(System.lineSeparator());

        return text.toString();
    }


    public static String buildTiMarkdownText(TiVulInfo tiVulInfo) {
        StringBuilder text = new StringBuilder();

        text.append("# ").append(tiVulInfo.getVulName()).append("</br></br>\n");
        text.append("* CVE编号：").append(tiVulInfo.getCveId()).append(System.lineSeparator());
        text.append("* CNVD编号：").append(tiVulInfo.getCnvdId()).append(System.lineSeparator());
        text.append("* 披露日期：").append(tiVulInfo.getPublishTime()).append(System.lineSeparator());
        text.append("* 风险等级：").append(tiVulInfo.getRatingLevel()).append(System.lineSeparator());
        text.append("* 漏洞标签：").append(tiVulInfo.getTags()).append(System.lineSeparator());
        text.append("* 漏洞类型：").append(tiVulInfo.getVulType()).append(System.lineSeparator());

        text.append("### 漏洞描述：").append(System.lineSeparator());
        text.append(tiVulInfo.getDescription()).append("</br></br>\n");

        return text.toString();
    }


    public static String buildAvdMarkdownText(AvdEntity avdEntity) {
        StringBuilder text = new StringBuilder();

        text.append("# ").append(avdEntity.getVulName()).append("</br></br>\n");
        text.append("* CVE编号：").append(avdEntity.getCveNumber()).append(System.lineSeparator());
        text.append("* 漏洞评级：").append(avdEntity.getLevel()).append(System.lineSeparator());
        text.append("* 披露日期：").append(avdEntity.getVulDate()).append(System.lineSeparator());
        text.append("* POC情况：").append(avdEntity.getExpStatus()).append("</br></br>\n");

        text.append("### 漏洞描述：").append(System.lineSeparator());
        text.append(avdEntity.getDescription()).append("</br></br>\n");

        text.append("### 修复建议：").append("\n\n");
        text.append(avdEntity.getSuggest()).append("</br></br>\n");

        text.append("### 参考链接：").append("\n\n");
        text.append(avdEntity.getReference());

        return text.toString();
    }

    public static String buildCisaMarkdownText(CisaVulInfo cisaVulInfo) {
        StringBuilder text = new StringBuilder();

        text.append("# ").append(cisaVulInfo.getVulnerabilityName()).append("</br></br>\n");
        text.append("* CVE编号：").append(cisaVulInfo.getCveNumber()).append(System.lineSeparator());
        text.append("* 披露日期：").append(cisaVulInfo.getDateAdded()).append(System.lineSeparator());
        text.append("* 供应商：").append(cisaVulInfo.getVendor()).append(System.lineSeparator());
        text.append("* 产品名称：").append(cisaVulInfo.getProduct()).append("</br></br>\n");

        text.append("### 漏洞描述：").append(System.lineSeparator());
        text.append(cisaVulInfo.getDescription()).append("</br></br>\n");

        text.append("### 修复建议：").append("\n\n");
        text.append(cisaVulInfo.getSuggest()).append("</br></br>\n");

        text.append("### 参考链接：").append("\n\n");
        text.append(cisaVulInfo.getReference());
        return text.toString();
    }

    public static String buildOscsMarkdownText(OscsVulInfo oscsVulInfo) {
        StringBuilder text = new StringBuilder();

        text.append("# ").append(oscsVulInfo.getTitle()).append("</br></br>\n");
        text.append("* CVE编号：").append(oscsVulInfo.getCveId()).append(System.lineSeparator());
        text.append("* CNVD编号：").append(oscsVulInfo.getCnvdId()).append(System.lineSeparator());
        text.append("* 披露日期：").append(oscsVulInfo.getPublicTime()).append(System.lineSeparator());
        text.append("* 风险等级：").append(oscsVulInfo.getLevel()).append(System.lineSeparator());
        text.append("* 漏洞类型：").append(oscsVulInfo.getVulnType()).append(System.lineSeparator());

        text.append("### 漏洞描述：").append(System.lineSeparator());
        text.append(oscsVulInfo.getDescription()).append("</br></br>\n");

        text.append("### 修复建议：").append("\n\n");
        text.append(oscsVulInfo.getSuggest()).append("</br></br>\n");

        text.append("### 参考链接：").append("\n\n");
        text.append(oscsVulInfo.getReference());
        return text.toString();
    }

    public static String buildMSVulMarkdownText(String cveNumber, String releaseDate, String tag, String mitreUrl) {
        StringBuilder text = new StringBuilder();
        text.append("# 微软漏洞提醒").append("\n\n");
        text.append("- **CVE编号：**: ").append(cveNumber).append(System.lineSeparator());
        text.append("- **发布时间**: ").append(releaseDate).append(System.lineSeparator());
        text.append("- **描述**: ").append(tag).append(System.lineSeparator());
        text.append("- **mitre地址**: [").append(mitreUrl);
        return text.toString();
    }


    public static void sendMarkdownMessage(String content) {
        try {
            String webhookUrl = String.valueOf(GlobalConfig.globalConfig.get("webhook"));
            long timestamp = System.currentTimeMillis();
            String secret = String.valueOf(GlobalConfig.globalConfig.get("secretKey"));

            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            BASE64Encoder base64Encoder = new BASE64Encoder();
            String sign = URLEncoder.encode(base64Encoder.encode(signData), "UTF-8");


            String urlStr = webhookUrl + "&timestamp=" + timestamp + "&sign=" + sign;

            // 构建JSON对象
            JSONObject json = new JSONObject();
            json.put("msgtype", "markdown");
            JSONObject markdown = new JSONObject();
            markdown.put("title", "项目动态");
            markdown.put("text", content);
            json.put("markdown", markdown);

            // 使用Hutool发送POST请求
            String response = HttpUtil.post(urlStr, json.toJSONString());
            System.out.println(response);
        } catch (Exception e) {
            log.error("消息发送失败：",e);
        }
    }

    public static void main(String[] args) {
        Map<String, Object> config = GlobalConfig.globalConfig;
        config.get("");
        AvdEntity avdEntity = new AvdEntity();
        avdEntity.setVulName("范围OA sql注入");
        avdEntity.setCveNumber("cve-xxxx-xxxx");
        avdEntity.setExpStatus("POC已公开");
        avdEntity.setLevel("严重");
        avdEntity.setVulDate("2024-07-06");
        avdEntity.setDescription("csacscsacscascsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        avdEntity.setVulType("sql注入");
        avdEntity.setReference("https://www.baidu.com");
        String content= DingTalkRobot.buildAvdMarkdownText(avdEntity);
        DingTalkRobot.sendMarkdownMessage(content);
    }
}
