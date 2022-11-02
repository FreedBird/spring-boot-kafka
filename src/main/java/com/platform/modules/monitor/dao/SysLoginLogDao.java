package com.platform.modules.monitor.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.monitor.domain.SysLoginLog;

import java.util.List;

/**
 * 系统访问日志情况信息 数据层
 */
public interface SysLoginLogDao extends BaseMapper<SysLoginLog> {


    /**
     * 查询系统登录日志集合
     *
     * @param sysLoginLog 访问日志对象
     * @return 登录记录集合
     */
    List<SysLoginLog> queryList(SysLoginLog sysLoginLog);

}
