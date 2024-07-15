package com.rabbitq.job;

import com.rabbitq.models.InfoGatherInterface;
import com.rabbitq.utils.GlobalConfig;
import com.rabbitq.utils.InfoGatherClassScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static com.rabbitq.utils.GlobalConfig.init;

/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-06-14
 * @Description: 定时任务实现
 * @Version: 1.0
 */


public class ThreatIntelligenceJob implements Job {
    private static final Logger log = LogManager.getLogger(ThreatIntelligenceJob.class);

    @Override
    public void execute(JobExecutionContext context) {
        // 获取当前系统时间
        LocalDateTime now = LocalDateTime.now();

        // 将系统时间转换为北京时间
        ZonedDateTime beijingTime = now.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("Asia/Shanghai"));

        // 获取北京时间的时分
        int hour = beijingTime.getHour();
//        int minute = beijingTime.getMinute();

        // 判断是否在北京时间1点至8点之间
        if (hour >= 1 && hour < 8) {
            return;
        }
        String initFile = System.getProperty("user.dir") + "/init";
        File file = new File(initFile);
        if (file.exists()) {
            GlobalConfig.init = true;
        }

        // 执行您的任务，使用配置信息
        log.info("开始抓取情报: {}", new java.util.Date());

        // ... 实现威胁情报抓取的逻辑
        List<InfoGatherInterface> implementations = InfoGatherClassScanner.scan();

        for (InfoGatherInterface impl : implementations) {
            try {
                impl.getRepos();
                //System.out.println(impl.getClass().getName());
            } catch (Exception e) {
                // 捕捉子类方法执行的异常
                System.out.println("Exception in calling method : "+impl.getClass().getName());
                log.error(e);
            }
        }
        if(!file.exists()){
            try {
                file.createNewFile();
                init=true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}