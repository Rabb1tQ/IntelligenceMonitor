package com.rabbitq.entity;

import lombok.Data;

/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-07-05
 * @Description: 阿里云漏洞库实体类
 * @Version: 1.0
 */

@Data
public class AvdEntity {
    private int id;
    private String vulName;
    private String vulType;
    private String vulDate;
    private String cveNumber;
    private String expStatus;
    private String Description;
    private String Suggest;
    private String Reference;
    private String Level;
}
