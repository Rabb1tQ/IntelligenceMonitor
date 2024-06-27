package com.rabbitq;

import com.rabbitq.scheduler.QuartzScheduler;
import com.rabbitq.utils.GlobalConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Map<String, Object> config = GlobalConfig.globalConfig;
        if (config != null) {
            Map<String, Object> scheduleConfig = (Map<String, Object>) config.get("schedule");
            if (scheduleConfig != null) {
                String intervalStr = (String) scheduleConfig.get("interval");
                Long interval = GlobalConfig.parseInterval(intervalStr);
                QuartzScheduler.scheduleJob(interval, config);
            }

        } else {
            logger.error("配置文件读取失败，无法启动调度器。");
        }
    }
}