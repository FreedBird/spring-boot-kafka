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
 * 字典数据表
 */
@Data
@TableName("sys_dict_data")
public class SysDictData {
    private static final long serialVersionUID = 1L;

    /**
     * 字典编码
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dictCode;

    /**
     * 字典排序
     */
    private Integer dictSort;

    /**
     * 字典标签
     */
    @NotBlank(message = "字典标签不能为空")
    @Size(max = 100, message = "字典标签长度不能超过100个字符")
    private String dictLabel;

    /**
     * 字典键值
     */
    @NotBlank(message = "字典键值不能为空")
    @Size(max = 100, message = "字典键值长度不能超过100个字符")
    private String dictValue;

    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    @Size(max = 100, message = "字典类型长度不能超过100个字符")
    private String dictType;

    /**
     * 样式属性（其他样式扩展）
     */
    @Size(max = 100, message = "样式属性长度不能超过100个字符")
    private String cssClass;

    /**
     * 表格字典样式
     */
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     */
    private YesOrNoEnum isDefault;

    /**
     * 状态（0正常 1停用）
     */
    private YesOrNoEnum status;

    /**
     * 备注
     */
    private String remark;

}
