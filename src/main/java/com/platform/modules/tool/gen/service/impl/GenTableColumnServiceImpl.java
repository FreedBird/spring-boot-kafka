package com.platform.modules.tool.gen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.common.web.service.impl.BaseServiceImpl;
import com.platform.modules.tool.gen.dao.GenTableColumnDao;
import com.platform.modules.tool.gen.domain.GenTableColumn;
import com.platform.modules.tool.gen.service.GenTableColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务字段 服务层实现
 */
@Service
public class GenTableColumnServiceImpl extends BaseServiceImpl<GenTableColumn> implements GenTableColumnService {

    @Resource
    private GenTableColumnDao columnDao;

    @Autowired
    public void setBaseDao() {
        super.setBaseDao(columnDao);
    }

    /**
     * 查询业务字段列表
     *
     * @return 业务字段集合
     */
    @Override
    public List<GenTableColumn> queryByTableId(Long tableId) {
        GenTableColumn column = new GenTableColumn();
        column.setTableId(tableId);
        return columnDao.selectList(new QueryWrapper<>(column));
    }

    @Override
    public void deleteByTableIds(Long[] tableIds) {
        columnDao.deleteByTableIds(tableIds);
    }

}