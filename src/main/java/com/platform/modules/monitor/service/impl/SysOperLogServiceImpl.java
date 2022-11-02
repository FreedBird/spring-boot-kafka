package com.platform.modules.monitor.service.impl;

import com.platform.common.web.service.impl.BaseServiceImpl;
import com.platform.modules.monitor.dao.SysOperLogDao;
import com.platform.modules.monitor.domain.SysOperLog;
import com.platform.modules.monitor.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 操作日志 服务层处理
 */
@Service
public class SysOperLogServiceImpl extends BaseServiceImpl<SysOperLog> implements SysOperLogService {

    @Resource
    private SysOperLogDao operLogDao;

    @Autowired
    public void setBaseDao() {
        super.setBaseDao(operLogDao);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SysOperLog> queryList(SysOperLog operLog) {
        return operLogDao.queryList(operLog);
    }

}
