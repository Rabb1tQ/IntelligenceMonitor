package com.rabbitq.utils;

import org.apache.ibatis.jdbc.SqlRunner;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * @Author: Rabb1tQ
 * @CreateTime: 2024-06-24
 * @Description: 数据库初始化
 * @Version: 1.0
 *
 *
 */


public class DatabaseInitializer {

    private static final String DB_URL = "jdbc:sqlite:your_database_file.db";

    public static void initializeDatabase() {
        // 检查数据库文件是否存在
//        File dbFile = new File("your_database_file.db");
//        if (!dbFile.exists()) {
//            try (Connection connection = DriverManager.getConnection(DB_URL)) {
//                // 创建 SqlRunner 实例
//                SqlRunner sqlRunner = new SqlRunner(connection.getClass());
//
//                // 创建 CVE 监控表
//                sqlRunner.execute("CREATE TABLE IF NOT EXISTS cve_monitor_github (" +
//                        "id INTEGER NOT NULL PRIMARY KEY," +
//                        "cve_name VARCHAR(255) NOT NULL," +
//                        "pushed_at VARCHAR(255) NOT NULL," +
//                        "cve_url VARCHAR(255) NOT NULL)");
//
//                // 创建 CVE 监控 MS 表
//                sqlRunner.execute("CREATE TABLE IF NOT EXISTS cve_monitor_ms (" +
//                        "cve_title VARCHAR(255) NOT NULL," +
//                        "cve_number VARCHAR(255) NOT NULL PRIMARY KEY," +
//                        "release_date VARCHAR(255) NOT NULL," +
//                        "mitre_url VARCHAR(255) NOT NULL," +
//                        "tag VARCHAR(255) NOT NULL)");
//
//                // 创建红队工具监控表
//                sqlRunner.execute("CREATE TABLE IF NOT EXISTS redteam_tools_monitor_github (" +
//                        "id INTEGER NOT NULL PRIMARY KEY," +
//                        "tools_name VARCHAR(255) NOT NULL," +
//                        "pushed_at VARCHAR(255) NOT NULL," +
//                        "tag_name VARCHAR(255) NOT NULL)");
//
//                // 创建用户监控表
//                sqlRunner.execute("CREATE TABLE IF NOT EXISTS user_monitor_github (" +
//                        "id INTEGER NOT NULL PRIMARY KEY," +
//                        "repo_name VARCHAR(255) NOT NULL)");
//
//                // 这里可以添加初始化数据的插入语句
//                // sqlRunner.execute("INSERT INTO ...");
//
//                System.out.println("Database initialized successfully.");
//            } catch (SQLException e) {
//                throw new MybatisPlusException("Error initializing database", e);
//            }
//        } else {
//            System.out.println("Database file already exists. Skipping initialization.");
//        }
    }

}