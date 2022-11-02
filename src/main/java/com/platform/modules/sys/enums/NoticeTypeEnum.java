package com.platform.modules.sys.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 通知类型
 */
public enum NoticeTypeEnum {

    /**
     * 通知
     */
    ONE("1", "通知"),
    /**
     * 公告
     */
    TWO("2", "公告");

    @EnumValue
    @JsonValue
    private String code;
    private String name;

    // 构造方法
    NoticeTypeEnum(String code, String name) {
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
