package com.rabbitq.entity;

import lombok.Data;

/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-06-24
 * @Description: CVE的实体类
 * @Version: 1.0
 */

@Data
public class CveMonitor {
    private Integer id;
    private String cveName;
    private String pushedAt;
    private String cveUrl;

}