package com.rabbitq.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

public class DingTalkRobot {

    public static String buildGithubMarkdownText(String userName, String repositoryName, String description, String repositoryUrl) {
        StringBuilder text = new StringBuilder();
        text.append("# github仓库监控：").append("\n\n");
        text.append("- **用户名**: ").append(userName).append(System.lineSeparator());
        text.append("- **仓库名称**: ").append(repositoryName).append(System.lineSeparator());
        text.append("- **描述**: ").append(description).append(System.lineSeparator());
        text.append("- **仓库地址**: [").append(repositoryName).append("](").append(repositoryUrl).append(")");
        return text.toString();
    }

    public static String buildMSVulMarkdownText(String cveNumber, String releaseDate, String tag, String mitreUrl) {
        StringBuilder text = new StringBuilder();
        text.append("# ").append("\n\n");
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

            System.out.println(sign);
            // 构建请求URL

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
            e.printStackTrace();
        }
    }
}
