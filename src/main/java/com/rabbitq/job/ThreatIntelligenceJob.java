package com.rabbitq.job;

import com.rabbitq.models.InfoGatherInterface;
import com.rabbitq.utils.InfoGatherClassScanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.rabbitq.utils.GlobalConfig.init;

/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-06-14
 * @Description: 定时任务实现
 * @Version: 1.0
 */


public class ThreatIntelligenceJob implements Job {

    private static final Logger logger = LogManager.getLogger(ThreatIntelligenceJob.class);

    @Override
    public void execute(JobExecutionContext context) {
//        JobDataMap dataMap = context.getMergedJobDataMap();
//        Map<String, Object> config = (Map<String, Object>) dataMap.get("config");
        // 执行您的任务，使用配置信息
        logger.info("开始抓取情报: {}", new java.util.Date());

        // ... 实现威胁情报抓取的逻辑
        List<InfoGatherInterface> implementations = InfoGatherClassScanner.scan();

        for (InfoGatherInterface impl : implementations) {
            try {
                impl.getRepos();
                //System.out.println(impl.getClass().getName());
            } catch (Exception e) {
                // 捕捉子类方法执行的异常
                System.out.println("Exception in calling method : "+impl.getClass().getName());
                System.out.println("具体异常："+e.getMessage());
            }
        }
        String initFile=System.getProperty("user.dir") + "/SubDic";
        File file = new File(initFile);
        if(file.exists()){
            try {
                file.createNewFile();
                init=true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            init=true;
        }

    }
}