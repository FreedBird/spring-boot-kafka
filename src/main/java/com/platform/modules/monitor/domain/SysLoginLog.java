package com.platform.modules.monitor.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.platform.common.enums.YesOrNoEnum;
import lombok.Data;

import java.util.Date;

/**
 * 系统访问记录表
 */
@Data
@TableName("sys_login_log")
public class SysLoginLog {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long infoId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 登录状态
     */
    private YesOrNoEnum status;

    /**
     * 登录IP地址
     */
    private String ipAddr;

    /**
     * 登录地点
     */
    private String ipLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 开始时间
     */
    @JsonIgnore
    @TableField(exist = false)
    private String beginTime;

    /**
     * 结束时间
     */
    @JsonIgnore
    @TableField(exist = false)
    private String endTime;

}