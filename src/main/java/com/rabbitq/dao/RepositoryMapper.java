package com.rabbitq.dao;

import com.rabbitq.entity.Repository;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-06-24
 * @Description:
 * @Version: 1.0
 */

@Mapper
public interface RepositoryMapper {
    Repository selectByRepoName(String repo_name);
    int insert(Repository record);
    int update(Repository record);
}