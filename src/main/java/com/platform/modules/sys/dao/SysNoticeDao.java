package com.platform.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.sys.domain.SysNotice;

import java.util.List;

/**
 * 通知公告表 数据层
 */
public interface SysNoticeDao extends BaseMapper<SysNotice> {

    /**
     * 查询管理数据
     *
     * @param notice
     * @return
     */
    List<SysNotice> queryList(SysNotice notice);
}