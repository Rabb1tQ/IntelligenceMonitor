package com.rabbitq.dao;

import com.rabbitq.entity.AvdEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-07-07
 * @Description: 阿里云漏洞库
 * @Version: 1.0
 */

@Mapper
public interface AvdMapper {
    AvdEntity selectByVulName(String vul_name);
    int insert(AvdEntity avdEntity);
}
