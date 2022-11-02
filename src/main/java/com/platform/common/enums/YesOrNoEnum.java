package com.platform.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 是否类型枚举
 */
public enum YesOrNoEnum {

    /**
     * 是
     */
    YES("Y", "是"),
    /**
     * 否
     */
    NO("N", "否");

    @EnumValue
    @JsonValue
    private final String code;
    private final String info;

    YesOrNoEnum(String code, String info) {
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
