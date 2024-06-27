package com.rabbitq.scheduler;

import com.rabbitq.job.ThreatIntelligenceJob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Map;

/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-06-14
 * @Description: 初始化和配置Quartz调度器
 * @Version: 1.0
 */


public class QuartzScheduler { private static final Logger logger = LogManager.getLogger(QuartzScheduler.class);

    public static void scheduleJob(Long intervalInMilliseconds, Map<String, Object> config) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("config", config);

            JobDetail job = JobBuilder.newJob(ThreatIntelligenceJob.class)
                    .withIdentity("threatIntelligenceJob", "group1")
                    .usingJobData(jobDataMap)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("threatIntelligenceJob", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInMilliseconds(intervalInMilliseconds)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.error("Quartz调度器配置失败", e);
        }
    }
}