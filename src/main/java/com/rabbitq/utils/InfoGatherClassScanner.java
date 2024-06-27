package com.rabbitq.utils;

import com.rabbitq.annotations.InfoGatherInterfaceImplementation;
import com.rabbitq.models.InfoGatherInterface;
import org.reflections.Reflections;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InfoGatherClassScanner {
    public static List<InfoGatherInterface> scan() {
        Set<Class<?>> classes = new Reflections("com.rabbitq.models.impl")  // 替换为您的包名
                .getTypesAnnotatedWith(InfoGatherInterfaceImplementation.class);

        return classes.stream()
                .map(clazz -> {
                    try {
                        return (InfoGatherInterface) clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }
}