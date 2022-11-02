package com.platform.common.shiro.vo;

import com.platform.common.shiro.enums.ShiroUserTypeEnum;
import com.platform.modules.sys.domain.SysRole;
import com.platform.modules.sys.domain.SysUser;
import lombok.Data;

import java.util.Set;

/**
 * 登录用户身份权限
 */
@Data
public class LoginUser {

    /**
     * 用户唯一标识
     */
    private String tokenId;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 用户类型
     */
    private ShiroUserTypeEnum userType;

    /**
     * 用户信息
     */
    private SysUser sysUser;

    /**
     * 角色信息
     */
    private SysRole sysRole;

    /**
     * 登陆时间
     */
    private Long loginTime;

    /**
     * 登录IP地址
     */
    private String ipAddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    public LoginUser(SysUser sysUser, SysRole sysRole, Set<String> permissions) {
        this.sysUser = sysUser;
        this.sysRole = sysRole;
        this.permissions = permissions;
        this.userType = ShiroUserTypeEnum.SYS_USER;
    }

}
