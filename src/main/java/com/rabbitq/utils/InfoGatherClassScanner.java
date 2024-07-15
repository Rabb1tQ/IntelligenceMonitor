package com.rabbitq.utils;

import com.rabbitq.annotations.InfoGatherInterfaceImplementation;
import com.rabbitq.models.InfoGatherInterface;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InfoGatherClassScanner {
    private static final Logger log = LoggerFactory.getLogger(InfoGatherClassScanner.class);

    public static List<InfoGatherInterface> scan() {
        Set<Class<?>> classes = new Reflections("com.rabbitq.models.impl")  // 替换为您的包名
                .getTypesAnnotatedWith(InfoGatherInterfaceImplementation.class);

        return classes.stream()
                .map(clazz -> {
                    try {
                        return (InfoGatherInterface) clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }
}