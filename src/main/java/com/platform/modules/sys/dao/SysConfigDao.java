package com.platform.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.sys.domain.SysConfig;

import java.util.List;

/**
 * 参数配置 数据层
 */
public interface SysConfigDao extends BaseMapper<SysConfig> {

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    List<SysConfig> queryList(SysConfig config);

}