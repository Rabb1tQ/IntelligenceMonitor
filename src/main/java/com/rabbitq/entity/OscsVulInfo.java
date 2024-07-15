package com.rabbitq.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName oscs_vul_info
 */
@Data
public class OscsVulInfo implements Serializable {
    private String publicTime;
    private String title;
    private String level;
    private String description;
    private String suggest;
    private String reference;
    private String vulnType;
    private String cveId;
    private String cnvdId;
}