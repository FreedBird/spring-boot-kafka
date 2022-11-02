package com.platform.modules.sys.service;

import com.platform.common.web.service.BaseService;
import com.platform.modules.sys.domain.SysDictType;

/**
 * 字典 业务层
 */
public interface SysDictTypeService extends BaseService<SysDictType> {

    /**
     * 删除字典信息
     *
     * @param dictId 需要删除的字典ID
     * @return 结果
     */
    Integer deleteDictType(Long dictId);

    /**
     * 新增保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    Integer addDictType(SysDictType dictType);

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    Integer updateDictType(SysDictType dictType);
}
