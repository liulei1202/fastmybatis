package com.myapp.fill;

import com.gitee.fastmybatis.core.handler.CustomIdFill;

import java.util.Optional;
import java.util.UUID;

/**
 * 演示自定义uuid，查看Address.java
 */
public class UUIDFill extends CustomIdFill<String> {

    @Override
    public String getColumnName() {
        return "id"; // 作用在id字段上
    }

    @Override
    protected Object getFillValue(String defaultValue) {
        return Optional.ofNullable(defaultValue).orElse(UUID.randomUUID().toString()); // 自定义的uuid生成方式
    }
}