package com.rabbitq.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CisaVulInfo implements Serializable {
    
    private String cveNumber;
    /**
     * 供应商
     */
    private String vendor;
    private String product;
    private String vulnerabilityName;
    /**
     * 漏洞公开日期
     */
    private String dateAdded;
    private String description;
    /**
     * 缓解措施
     */
    private String suggest;
    /**
     * 引用
     */
    private String reference;


}