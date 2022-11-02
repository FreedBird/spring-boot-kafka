package com.platform.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 性别类型枚举
 */
public enum GenderTypeEnum {

    /**
     * 男
     */
    MALE("1", "男"),
    /**
     * 女
     */
    FEMALE("0", "女");

    @EnumValue
    @JsonValue
    private final String code;
    private final String info;

    GenderTypeEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
