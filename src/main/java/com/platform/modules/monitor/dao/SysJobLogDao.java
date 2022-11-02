package com.platform.modules.monitor.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.monitor.domain.SysJobLog;

import java.util.List;

/**
 * 调度任务日志信息 数据层
 */
public interface SysJobLogDao extends BaseMapper<SysJobLog> {

    /**
     * 获取quartz调度器日志的计划任务
     *
     * @param jobLog 调度日志信息
     * @return 调度任务日志集合
     */
    List<SysJobLog> queryList(SysJobLog jobLog);

}
