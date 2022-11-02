package com.platform.modules.monitor.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.monitor.domain.SysOperLog;

import java.util.List;

/**
 * 操作日志 数据层
 */
public interface SysOperLogDao extends BaseMapper<SysOperLog> {

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    List<SysOperLog> queryList(SysOperLog operLog);

}
