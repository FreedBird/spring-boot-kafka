package com.platform.modules.monitor.controller;

import com.platform.common.aspectj.lang.annotation.Log;
import com.platform.common.aspectj.lang.enums.BusinessType;
import com.platform.common.web.controller.BaseController;
import com.platform.common.web.domain.AjaxResult;
import com.platform.common.web.page.TableDataInfo;
import com.platform.modules.monitor.domain.SysOperLog;
import com.platform.modules.monitor.service.SysOperLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 操作日志记录
 */
@RestController
@RequestMapping("/monitor/operLog")
public class SysOperLogController extends BaseController {

    private final static String title = "操作日志";

    @Resource
    private SysOperLogService operLogService;

    @RequiresPermissions("monitor:operLog:list")
    @GetMapping("/list")
    public TableDataInfo list(SysOperLog operLog) {
        startPage();
        List<SysOperLog> list = operLogService.queryList(operLog);
        return getDataTable(list);
    }

    @RequiresPermissions("monitor:operLog:remove")
    @GetMapping("/delete/{operIds}")
    @Log(title = title, businessType = BusinessType.DELETE)
    public AjaxResult remove(@PathVariable Long[] operIds) {
        return toAjax(operLogService.deleteByIds(operIds));
    }

}
