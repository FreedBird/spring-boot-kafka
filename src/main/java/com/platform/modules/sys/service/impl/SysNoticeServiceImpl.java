package com.platform.modules.sys.service.impl;

import com.platform.common.web.service.impl.BaseServiceImpl;
import com.platform.modules.sys.dao.SysNoticeDao;
import com.platform.modules.sys.domain.SysNotice;
import com.platform.modules.sys.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公告 服务层实现
 */
@Service
public class SysNoticeServiceImpl extends BaseServiceImpl<SysNotice> implements SysNoticeService {

    @Resource
    private SysNoticeDao noticeDao;

    @Autowired
    public void setBaseDao() {
        super.setBaseDao(noticeDao);
    }

    @Override
    public List<SysNotice> queryList(SysNotice notice) {
        return noticeDao.queryList(notice);
    }
}
