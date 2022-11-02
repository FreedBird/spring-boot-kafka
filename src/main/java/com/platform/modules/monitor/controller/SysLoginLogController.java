package com.platform.modules.monitor.controller;

import com.platform.common.aspectj.lang.annotation.Log;
import com.platform.common.aspectj.lang.enums.BusinessType;
import com.platform.common.web.controller.BaseController;
import com.platform.common.web.domain.AjaxResult;
import com.platform.common.web.page.TableDataInfo;
import com.platform.modules.monitor.domain.SysLoginLog;
import com.platform.modules.monitor.service.SysLoginLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统访问记录
 */
@RestController
@RequestMapping("/monitor/loginLog")
public class SysLoginLogController extends BaseController {

    private final static String title = "登陆日志";

    @Autowired
    private SysLoginLogService loginLogService;

    @RequiresPermissions("monitor:loginLog:list")
    @GetMapping("/list")
    public TableDataInfo list(SysLoginLog loginLog) {
        startPage();
        List<SysLoginLog> list = loginLogService.queryList(loginLog);
        return getDataTable(list);
    }

    @RequiresPermissions("monitor:loginLog:remove")
    @Log(title = title, businessType = BusinessType.DELETE)
    @GetMapping("/delete/{infoIds}")
    public AjaxResult remove(@PathVariable Long[] infoIds) {
        return toAjax(loginLogService.deleteByIds(infoIds));
    }

}
