package com.platform.common.constant;

/**
 * 通用常量信息
 */
public class Constants {

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_KEY = "sys:captcha:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_TIMEOUT = 2;

    /**
     * cookie_name
     */
    public static final String COOKIE_NAME = "Admin-Token";

    /**
     * 登录用户 redis key
     */
    public static final String TOKEN_SYS = "token:sys:";

    /**
     * 令牌
     */
    public static final String TOKEN_SYS_KEY = "Authorization";

    /**
     * 权限配置 Perm
     */
    public static final String PERM_SYS = "sys:sys:sys:sys";

    /**
     * 登录用户 redis key
     */
    public static final String TOKEN_APP = "token:app:";

    /**
     * APP_TOKEN
     */
    public static final String TOKEN_APP_KEY = "AppAuthorization";

    /**
     * 权限配置 Perm
     */
    public static final String PERM_APP = "app:app:app:app";

    /**
     * 前缀 Perm
     */
    public static final String PREFIX_APP = "/app/";

    /**
     * 权限配置 Perm
     */
    public static final String PERM_SUPER = "*:*:*:*";

}
