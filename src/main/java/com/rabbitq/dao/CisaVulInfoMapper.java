package com.rabbitq.dao;

import com.rabbitq.entity.CisaVulInfo;
import org.apache.ibatis.annotations.Mapper;

/**
* @author test
* @description 针对表【cisa_vul_info】的数据库操作Mapper
* @createDate 2024-07-08 00:33:41
* @Entity com.rabbitq.entity.CisaVulInfo
*/
@Mapper
public interface CisaVulInfoMapper {
    CisaVulInfo selectByCveNumber(String cveNumber);
    int insert(CisaVulInfo cisaVulInfo);
}




