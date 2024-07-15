package com.rabbitq.utils;

import lombok.Getter;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-06-25
 * @Description: mybatis工具类
 * @Version: 1.0
 */


public class MyBatisUtil {
    private static final Logger log = LoggerFactory.getLogger(MyBatisUtil.class);
    @Getter
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            String resource = "mybatis-config.xml"; // MyBatis 配置文件的路径
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            log.error("配置文件读取失败",e);
        }
    }


    public static <T> T getMapper(Class<T> clazz) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.getMapper(clazz);
        }
    }
}