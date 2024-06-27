package com.rabbitq.entity;

import lombok.Data;

/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-06-24
 * @Description: 用户监控实体类
 * @Version: 1.0
 */

@Data
public class Repository {
    private Integer id;
    private String repoName;
    private String pushedAt;
    private String description;
}
