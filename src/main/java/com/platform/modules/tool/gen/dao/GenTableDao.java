package com.platform.modules.tool.gen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.tool.gen.domain.GenTable;

import java.util.List;

/**
 * 业务 数据层
 */
public interface GenTableDao extends BaseMapper<GenTable> {
    /**
     * 查询业务列表
     *
     * @param genTable 业务信息
     * @return 业务集合
     */
    List<GenTable> queryList(GenTable genTable);

    /**
     * 查询据库列表
     *
     * @param genTable 业务信息
     * @return 数据库表集合
     */
    List<GenTable> queryDbTableList(GenTable genTable);

    /**
     * 查询据库列表
     *
     * @param tableNames 表名称组
     * @return 数据库表集合
     */
    List<GenTable> queryDbTableListByNames(String[] tableNames);

    /**
     * 查询表名称业务信息
     *
     * @param tableName 表名称
     * @return 业务信息
     */
    GenTable queryDbTableByName(String tableName);

}