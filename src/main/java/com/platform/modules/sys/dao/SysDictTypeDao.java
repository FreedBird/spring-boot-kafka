package com.platform.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.sys.domain.SysDictType;

import java.util.List;

/**
 * 字典表 数据层
 */
public interface SysDictTypeDao extends BaseMapper<SysDictType> {

    /**
     * 查询管理数据
     *
     * @param dictType
     * @return
     */
    List<SysDictType> queryList(SysDictType dictType);

}
