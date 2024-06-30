package com.rabbitq.entity;

import lombok.Data;

/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-06-20
 * @Description: 微软漏洞信息实体类
 * @Version: 1.0
 */

@Data
public class CveMonitorMs {
    private String cveNumber;
    private String releaseDate;
    private String mitreUrl;
    private String tag;
}
