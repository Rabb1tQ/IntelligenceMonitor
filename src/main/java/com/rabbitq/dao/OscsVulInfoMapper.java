package com.rabbitq.dao;


import com.rabbitq.entity.AvdEntity;
import com.rabbitq.entity.OscsVulInfo;

/**
* @author test
* @description 针对表【oscs_vul_info】的数据库操作Mapper
* @createDate 2024-07-09 00:59:05
* @Entity generator.domain.OscsVulInfo
*/
public interface OscsVulInfoMapper  {
    OscsVulInfo selectByTitle(String title);
    int insert(OscsVulInfo oscsVulInfo);
}




