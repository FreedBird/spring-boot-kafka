package com.platform.modules.sys.service.impl;

import com.platform.common.exception.BaseException;
import com.platform.common.web.service.impl.BaseServiceImpl;
import com.platform.modules.sys.dao.SysDictTypeDao;
import com.platform.modules.sys.domain.SysDictData;
import com.platform.modules.sys.domain.SysDictType;
import com.platform.modules.sys.service.SysDictDataService;
import com.platform.modules.sys.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典 业务层处理
 */
@Service
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictType> implements SysDictTypeService {

    @Resource
    private SysDictTypeDao dictTypeDao;

    @Resource
    private SysDictDataService dictDataService;

    @Autowired
    public void setBaseDao() {
        super.setBaseDao(dictTypeDao);
    }

    /**
     * 查询管理数据
     *
     * @param dictType
     * @return
     */
    @Override
    public List<SysDictType> queryList(SysDictType dictType) {
        return dictTypeDao.queryList(dictType);
    }

    /**
     * 批量删除字典类型信息
     *
     * @param dictId 需要删除的字典ID
     * @return 结果
     */
    @Override
    public Integer deleteDictType(Long dictId) {
        SysDictType dictType = getById(dictId);
        if (dictType == null) {
            throw new BaseException("数据不存在");
        }
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType(dictType.getDictType());
        if (dictDataService.queryCount(sysDictData) > 0) {
            throw new BaseException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
        }
        return deleteById(dictId);
    }

    /**
     * 新增保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    @Override
    public Integer addDictType(SysDictType dictType) {
        uniqueName(dictType);
        uniqueType(dictType);
        return add(dictType);
    }

    /**
     * 校验名称是否唯一
     *
     * @return 结果
     */
    private void uniqueName(SysDictType dictType) {
        SysDictType query = new SysDictType();
        query.setDictName(dictType.getDictName());
        SysDictType queryOne = queryOne(query);
        boolean result = queryOne == null
                || !(dictType.getDictId() == null)
                || queryOne.getDictId().equals(dictType.getDictId());
        if (!result) {
            throw new BaseException("字典名称已存在");
        }
    }

    /**
     * 校验标识是否唯一
     *
     * @return 结果
     */
    private void uniqueType(SysDictType dictType) {
        SysDictType query = new SysDictType();
        query.setDictType(dictType.getDictType());
        SysDictType queryOne = queryOne(query);
        boolean result = queryOne == null
                || !(dictType.getDictId() == null)
                || queryOne.getDictId().equals(dictType.getDictId());
        if (!result) {
            throw new BaseException("字典类型已存在");
        }
    }

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateDictType(SysDictType dictType) {
        uniqueName(dictType);
        uniqueType(dictType);
        SysDictType oldDict = getById(dictType.getDictId());
        dictDataService.updateDictType(oldDict.getDictType(), dictType.getDictType());
        return updateById(dictType);
    }

}
