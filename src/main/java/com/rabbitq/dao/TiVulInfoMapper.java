package com.rabbitq.dao;

import com.rabbitq.entity.AvdEntity;
import com.rabbitq.entity.TiVulInfo;
import org.apache.ibatis.annotations.Mapper;

/**
* @author test
* @description 针对表【ti_vul_info】的数据库操作Mapper
* @createDate 2024-07-16 01:02:51
* @Entity generator.entity.TiVulInfo
*/
@Mapper
public interface TiVulInfoMapper {
    TiVulInfo selectByVulName(String vul_name);
    int insert(TiVulInfo tiVulInfo);
}




