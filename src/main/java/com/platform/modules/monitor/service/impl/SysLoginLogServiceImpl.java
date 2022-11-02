package com.platform.modules.monitor.service.impl;

import com.platform.common.web.service.impl.BaseServiceImpl;
import com.platform.modules.monitor.dao.SysLoginLogDao;
import com.platform.modules.monitor.domain.SysLoginLog;
import com.platform.modules.monitor.service.SysLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统访问日志情况信息 服务层处理
 */
@Service
public class SysLoginLogServiceImpl extends BaseServiceImpl<SysLoginLog> implements SysLoginLogService {

    @Resource
    private SysLoginLogDao loginLogDao;

    @Autowired
    public void setBaseDao() {
        super.setBaseDao(loginLogDao);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param sysLoginLog 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLoginLog> queryList(SysLoginLog sysLoginLog) {
        return loginLogDao.queryList(sysLoginLog);
    }

}
