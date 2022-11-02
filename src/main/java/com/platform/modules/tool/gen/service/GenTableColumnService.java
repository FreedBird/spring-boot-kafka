package com.platform.modules.tool.gen.service;

import com.platform.common.web.service.BaseService;
import com.platform.modules.tool.gen.domain.GenTableColumn;

import java.util.List;

/**
 * 业务字段 服务层
 */
public interface GenTableColumnService extends BaseService<GenTableColumn> {

    /**
     * 查询业务字段列表
     *
     * @param tableId 业务字段编号
     * @return 业务字段集合
     */
    List<GenTableColumn> queryByTableId(Long tableId);

    /**
     * 通过tableId删除
     *
     * @param tableIds
     */
    void deleteByTableIds(Long[] tableIds);

}
