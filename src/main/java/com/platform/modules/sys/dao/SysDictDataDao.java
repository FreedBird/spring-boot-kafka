package com.platform.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.sys.domain.SysDictData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典表 数据层
 */
public interface SysDictDataDao extends BaseMapper<SysDictData> {

    /**
     * 查询管理数据
     *
     * @param dictData
     * @return
     */
    List<SysDictData> queryList(SysDictData dictData);

    /**
     * 同步修改字典类型
     *
     * @param oldDictType 旧字典类型
     * @param newDictType 新旧字典类型
     * @return 结果
     */
    Integer updateDictType(@Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);
}
