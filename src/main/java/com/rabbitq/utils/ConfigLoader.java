package com.rabbitq.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public enum ConfigLoader {
    INSTANCE;

    private Map<String, Object> mapConfigEntity;

    ConfigLoader() {
        Yaml yaml = new Yaml();
        try{
            FileInputStream inputStream = new FileInputStream(new File("config.yml"));
            this.mapConfigEntity = yaml.load(inputStream);
        } catch (IOException e) {
            System.out.println("\033[31m配置文件读取失败：" + e);
        }
    }

    public Map<String, Object> getConfig() {
        return mapConfigEntity;
    }
}
