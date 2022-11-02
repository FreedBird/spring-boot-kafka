package com.platform.common.enums;

/**
 * 返回码枚举
 */
public enum ResultCodeEnum {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 未授权
     */
    UNAUTHORIZED(401, "token失效，请重新登录"),
    /**
     * 资源/服务未找到
     */
    NOT_FOUND(404, "路径不存在，请检查路径是否正确"),
    /**
     * 操作失败
     */
    FAIL(500, "系统异常，请联系管理员"),
    /**
     * 接口权限不足
     */
    AUTH(701, "接口权限不足"),
    /**
     * 提示消息
     */
    MSG(801, "提示消息");

    private final Integer code;
    private final String info;

    ResultCodeEnum(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
