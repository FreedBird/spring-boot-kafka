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
 * 定时任务调度日志表 sys_job_log
 */
@Data
@TableName("sys_job_log")
public class SysJobLog {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long jobLogId;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    private String invokeTarget;

    /**
     * 日志信息
     */
    private String jobMessage;

    /**
     * 执行状态（0正常 1失败）
     */
    private YesOrNoEnum status;

    /**
     * 异常信息
     */
    private String exceptionInfo;

    /**
     * 执行时间
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
     * 停止时间
     */
    @JsonIgnore
    @TableField(exist = false)
    private String endTime;

}
