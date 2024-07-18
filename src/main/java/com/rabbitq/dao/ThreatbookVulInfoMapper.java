package com.rabbitq.dao;

import com.rabbitq.entity.ThreatbookVulInfo;
import org.apache.ibatis.annotations.Mapper;

/**
* @author test
* @description 针对表【threatbook_vul_info】的数据库操作Mapper
* @createDate 2024-07-18 23:26:58
* @Entity generator.entity.ThreatbookVulInfo
*/
@Mapper
public interface ThreatbookVulInfoMapper {
    ThreatbookVulInfo selectByVulName(String vuln_name);
    int insert(ThreatbookVulInfo threatbookVulInfo);
}




