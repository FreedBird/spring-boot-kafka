package com.platform.modules.sys.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 系统用户枚举
 */
public enum SysUserTypeEnum {

    /**
     * 管理员
     */
    ADMIN("0", "管理员"),
    /**
     * 用户
     */
    USER("1", "用户");

    @EnumValue
    private final String code;
    private final String info;

    SysUserTypeEnum(String code, String info) {
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
