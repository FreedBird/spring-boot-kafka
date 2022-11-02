package com.platform.modules.tool.gen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.tool.gen.domain.GenTableColumn;

import java.util.List;

/**
 * 业务字段 数据层
 */
public interface GenTableColumnDao extends BaseMapper<GenTableColumn> {

    /**
     * 根据表名称查询列信息
     *
     * @param tableName 表名称
     * @return 列信息
     */
    List<GenTableColumn> queryByTableName(String tableName);

    /**
     * 通过tableId删除
     *
     * @param tableIds
     */
    void deleteByTableIds(Long[] tableIds);
}