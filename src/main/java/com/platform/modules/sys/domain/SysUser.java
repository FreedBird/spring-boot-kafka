package com.platform.modules.sys.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.platform.common.enums.GenderTypeEnum;
import com.platform.modules.sys.enums.SysUserTypeEnum;
import com.platform.common.enums.YesOrNoEnum;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 用户对象 sys_user
 */
@Data
@TableName("sys_user")
public class SysUser {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 用户账号
     */
    @NotBlank(message = "用户账号不能为空")
    @Size(max = 30, message = "用户账号长度不能超过30个字符")
    private String userName;

    /**
     * 用户昵称
     */
    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickName;

    /**
     * 用户类型 0=管理1=用户
     */
    @JsonIgnore
    private SysUserTypeEnum userType;

    /**
     * 用户邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /**
     * 手机号码
     */
    @Size(max = 11, message = "手机号码长度不能超过11个字符")
    private String phone;

    /**
     * 用户性别
     */
    private GenderTypeEnum sex;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * 盐加密
     */
    @JsonIgnore
    private String salt;

    /**
     * 帐号状态（0正常 1停用）
     */
    private YesOrNoEnum status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    /**
     * 角色名称
     */
    @TableField(exist = false)
    private String roleName;

    @JsonIgnore
    public boolean isAdmin() {
        return SysUserTypeEnum.ADMIN.equals(getUserType());
    }
}
