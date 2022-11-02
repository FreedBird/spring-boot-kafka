package com.platform.modules.sys.service.impl;

import com.platform.common.web.service.impl.BaseServiceImpl;
import com.platform.modules.sys.dao.SysDictDataDao;
import com.platform.modules.sys.domain.SysDictData;
import com.platform.modules.sys.service.SysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典 业务层处理
 */
@Service
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictData> implements SysDictDataService {

    @Resource
    private SysDictDataDao dictDataDao;

    @Autowired
    public void setBaseDao() {
        super.setBaseDao(dictDataDao);
    }

    /**
     * 查询管理数据
     *
     * @param dictData
     * @return
     */
    @Override
    public List<SysDictData> queryList(SysDictData dictData) {
        return dictDataDao.queryList(dictData);
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> queryByType(String dictType) {
        SysDictData dictData = new SysDictData();
        dictData.setDictType(dictType);
        return queryList(dictData);
    }

    @Override
    public Integer updateDictType(String oldDictType, String newDictType) {
        return dictDataDao.updateDictType(oldDictType, newDictType);
    }

}
