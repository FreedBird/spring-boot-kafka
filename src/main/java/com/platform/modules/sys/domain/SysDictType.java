package com.platform.modules.sys.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.platform.common.enums.YesOrNoEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 字典类型表 sys_dict_type
 */
@Data
@TableName("sys_dict_type")
public class SysDictType {

    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dictId;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    @Size(max = 100, message = "字典类型名称长度不能超过100个字符")
    private String dictName;

    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    @Size(max = 100, message = "字典类型类型长度不能超过100个字符")
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    private YesOrNoEnum status;

    /**
     * 备注
     */
    private String remark;

}
