package com.rabbitq.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @TableName ti_vul_info
 */
@Data
public class TiVulInfo implements Serializable {

    private String vulName;

    private String description;

    private String ratingLevel;

    private String publishTime;

    private String tags;

    private String vulType;

    private String cveId;

    private String cnvdId;



}