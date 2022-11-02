package com.platform.modules.tool.test.service.impl;

import com.platform.modules.sys.domain.SysUser;
import com.platform.modules.sys.service.SysUserService;
import com.platform.modules.tool.test.service.TestSourceService;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestSourceServiceImpl implements TestSourceService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public SysUser test1() {
        return sysUserService.getById(1L);
    }

    @Override
    public void test2() {
        SysUser sysUser = new SysUser();
        sysUser.setUserName("a");
        sysUser.setNickName("a");
        sysUserService.add(sysUser);
    }

    @Override
    public SysUser test3() {
        try (HintManager hintManager = HintManager.getInstance()) {
            // 设置强制访问主库
            hintManager.setMasterRouteOnly();
            // 执行查询
            return sysUserService.getById(1L);
        }
    }

}
