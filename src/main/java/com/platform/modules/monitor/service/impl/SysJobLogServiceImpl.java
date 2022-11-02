package com.platform.modules.monitor.service.impl;

import com.platform.common.web.service.impl.BaseServiceImpl;
import com.platform.modules.monitor.dao.SysJobLogDao;
import com.platform.modules.monitor.domain.SysJobLog;
import com.platform.modules.monitor.service.SysJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务调度日志信息 服务层
 */
@Service
public class SysJobLogServiceImpl extends BaseServiceImpl<SysJobLog> implements SysJobLogService {

    @Resource
    private SysJobLogDao jobLogDao;

    @Autowired
    public void setBaseDao() {
        super.setBaseDao(jobLogDao);
    }

    @Override
    public List<SysJobLog> queryList(SysJobLog jobLog) {
        return jobLogDao.queryList(jobLog);
    }

}
