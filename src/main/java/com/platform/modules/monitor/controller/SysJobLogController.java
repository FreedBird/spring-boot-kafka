package com.platform.modules.monitor.controller;

import com.platform.common.aspectj.lang.annotation.Log;
import com.platform.common.aspectj.lang.enums.BusinessType;
import com.platform.common.web.controller.BaseController;
import com.platform.common.web.domain.AjaxResult;
import com.platform.common.web.page.TableDataInfo;
import com.platform.modules.monitor.domain.SysJobLog;
import com.platform.modules.monitor.service.SysJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 调度日志操作处理
 */
@RestController
@RequestMapping("/monitor/jobLog")
public class SysJobLogController extends BaseController {

    private final static String title = "定时任务调度日志";

    @Autowired
    private SysJobLogService jobLogService;

    /**
     * 查询定时任务调度日志列表
     */
    @RequiresPermissions("monitor:job:list")
    @GetMapping("/list")
    public TableDataInfo list(SysJobLog sysJobLog) {
        startPage();
        List<SysJobLog> list = jobLogService.queryList(sysJobLog);
        return getDataTable(list);
    }

    /**
     * 根据调度编号获取详细信息
     */
    @RequiresPermissions("monitor:job:query")
    @GetMapping("/info/{jobLogId}")
    public AjaxResult getInfo(@PathVariable Long jobLogId) {
        return AjaxResult.success(jobLogService.getById(jobLogId));
    }

    /**
     * 删除定时任务调度日志
     */
    @RequiresPermissions("monitor:job:remove")
    @Log(title = title, businessType = BusinessType.DELETE)
    @GetMapping("/delete/{jobLogIds}")
    public AjaxResult remove(@PathVariable Long[] jobLogIds) {
        return toAjax(jobLogService.deleteByIds(jobLogIds));
    }

}
