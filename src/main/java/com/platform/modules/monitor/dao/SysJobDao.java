package com.platform.modules.monitor.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.monitor.domain.SysJob;

import java.util.List;

/**
 * 调度任务信息 数据层
 */
public interface SysJobDao extends BaseMapper<SysJob> {
    /**
     * 查询调度任务日志集合
     *
     * @param job 调度信息
     * @return 操作日志集合
     */
    List<SysJob> queryList(SysJob job);

}
