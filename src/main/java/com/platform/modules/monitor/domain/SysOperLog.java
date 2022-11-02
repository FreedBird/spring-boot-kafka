package com.platform.modules.monitor.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.platform.common.aspectj.lang.enums.BusinessType;
import com.platform.common.enums.YesOrNoEnum;
import lombok.Data;

import java.util.Date;

/**
 * 操作日志记录表
 */
@Data
@TableName("sys_oper_log")
public class SysOperLog {

    private static final long serialVersionUID = 1L;

    /**
     * 日志主键
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long operId;

    /**
     * 操作模块
     */
    private String title;

    /**
     * 业务类型
     */
    private BusinessType businessType;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作人员
     */
    private String operName;

    /**
     * 请求url
     */
    private String operUrl;

    /**
     * 操作地址
     */
    private String operIp;

    /**
     * 操作地点
     */
    private String operLocation;

    /**
     * 请求参数
     */
    private String operParam;

    /**
     * 返回参数
     */
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    private YesOrNoEnum status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;

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
