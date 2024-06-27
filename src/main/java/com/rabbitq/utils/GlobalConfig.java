package com.rabbitq.utils;

import java.util.Map;

public class GlobalConfig {
    public static final Map<String,Object> globalConfig= ConfigLoader.INSTANCE.getConfig();

    public static boolean init=false;

    public static Long parseInterval(String intervalStr) {
        // 根据实际情况解析时间字符串，转换为毫秒
        // 这里只是一个简单的例子，您可能需要更复杂的逻辑来处理不同的时间单位
        final long lTime = Long.parseLong(intervalStr.substring(0, intervalStr.length() - 2));

        if (intervalStr.endsWith("分钟")) {
            return lTime * 60 * 1000L;
        } else if (intervalStr.endsWith("小时")) {
            return lTime * 60 * 60 * 1000L;
        } else {
            throw new IllegalArgumentException("Unsupported time unit in config: " + intervalStr);
        }
    }
}
