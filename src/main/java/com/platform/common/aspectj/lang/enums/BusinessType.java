package com.platform.common.aspectj.lang.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 业务操作类型
 */
@Getter
public enum BusinessType {

    /**
     * 新增
     */
    ADD("1", "新增"),

    /**
     * 修改
     */
    EDIT("2", "修改"),

    /**
     * 删除
     */
    DELETE("3", "删除"),

    /**
     * 导出
     */
    EXPORT("4", "导出"),

    /**
     * 导入
     */
    IMPORT("5", "导入"),

    /**
     * 代码
     */
    CODE("6", "代码"),

    /**
     * 其它
     */
    OTHER("99", "其它");

    @EnumValue
    @JsonValue
    private final String code;
    private final String info;

    BusinessType(String code, String info) {
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
