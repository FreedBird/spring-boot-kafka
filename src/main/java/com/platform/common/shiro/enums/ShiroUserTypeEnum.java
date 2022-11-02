package com.platform.common.shiro.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 用户类型
 */
public enum ShiroUserTypeEnum {

    /**
     * 后台用户
     */
    SYS_USER("1", "后台用户"),
    /**
     * APP端用户
     */
    APP_USER("2", "APP端用户");

    @EnumValue
    private String code;
    private String name;

    // 构造方法
    ShiroUserTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
