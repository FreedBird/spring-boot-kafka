package com.platform.modules.sys.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.platform.common.enums.YesOrNoEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 角色表 sys_role
 */
@Data
@TableName("sys_role")
@NoArgsConstructor
public class SysRole {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 30, message = "角色名称长度不能超过30个字符")
    private String roleName;

    /**
     * 角色权限
     */
    @NotBlank(message = "权限字符不能为空")
    @Size(max = 100, message = "权限字符长度不能超过100个字符")
    private String roleKey;

    /**
     * 角色排序
     */
    @NotBlank(message = "显示顺序不能为空")
    private String roleSort;

    /**
     * 角色状态（0正常 1停用）
     */
    private YesOrNoEnum status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单组
     */
    @TableField(exist = false)
    @JsonProperty
    private Long[] menuIds;

    public SysRole(Long roleId) {
        this.roleId = roleId;
        if (0L == roleId) {
            this.roleName = "平台管理员";
            this.roleKey = "super";
        }
    }

}
