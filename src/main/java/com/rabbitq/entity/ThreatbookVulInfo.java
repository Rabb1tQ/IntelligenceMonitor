package com.rabbitq.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName threatbook_vul_info
 */
@Data
public class ThreatbookVulInfo implements Serializable {
    /**
     * 风险等级
     */
    private String riskLevel;

    private String tags;

    /**
     * 
     */
    private String vulnName;

    /**
     * 更新时间
     */
    private String vulnUpdateTime;



    /**
     * 
     */
    private String affects;


}