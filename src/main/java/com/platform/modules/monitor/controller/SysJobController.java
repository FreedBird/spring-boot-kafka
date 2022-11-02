package com.platform.modules.monitor.controller;

import com.platform.common.aspectj.lang.annotation.Log;
import com.platform.common.aspectj.lang.enums.BusinessType;
import com.platform.common.exception.BaseException;
import com.platform.common.utils.job.exception.TaskException;
import com.platform.common.utils.job.CronUtils;
import com.platform.common.web.controller.BaseController;
import com.platform.common.web.domain.AjaxResult;
import com.platform.common.web.page.TableDataInfo;
import com.platform.modules.monitor.domain.SysJob;
import com.platform.modules.monitor.service.SysJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 调度任务信息操作处理
 */
@RestController
@RequestMapping("/monitor/job")
public class SysJobController extends BaseController {

    private final static String title = "定时任务";

    @Autowired
    private SysJobService jobService;

    /**
     * 查询定时任务列表
     */
    @RequiresPermissions("monitor:job:list")
    @GetMapping("/list")
    public TableDataInfo list(SysJob sysJob) {
        startPage();
        List<SysJob> list = jobService.queryList(sysJob);
        return getDataTable(list);
    }

    /**
     * 获取定时任务详细信息
     */
    @RequiresPermissions("monitor:job:query")
    @GetMapping("/info/{jobId}")
    public AjaxResult getInfo(@PathVariable("jobId") Long jobId) {
        return AjaxResult.success(jobService.getById(jobId));
    }

    /**
     * 新增定时任务
     */
    @RequiresPermissions("monitor:job:add")
    @Log(title = title, businessType = BusinessType.ADD)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody SysJob sysJob) throws SchedulerException, TaskException {
        validCron(sysJob);
        return toAjax(jobService.addJob(sysJob));
    }

    /**
     * 校验cron表达式
     */
    private void validCron(SysJob sysJob) {
        if (!CronUtils.isValid(sysJob.getCronExpression())) {
            throw new BaseException("Cron执行表达式格式不正确");
        }
    }

    /**
     * 修改定时任务
     */
    @RequiresPermissions("monitor:job:edit")
    @Log(title = title, businessType = BusinessType.EDIT)
    @PostMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody SysJob sysJob) throws SchedulerException, TaskException {
        validCron(sysJob);
        return toAjax(jobService.updateJob(sysJob));
    }

    /**
     * 定时任务状态修改
     */
    @RequiresPermissions("monitor:job:changeStatus")
    @Log(title = title, businessType = BusinessType.EDIT)
    @PostMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysJob job) throws SchedulerException {
        SysJob newJob = jobService.getById(job.getJobId());
        newJob.setStatus(job.getStatus());
        return toAjax(jobService.changeStatus(newJob));
    }

    /**
     * 定时任务立即执行一次
     */
    @RequiresPermissions("monitor:job:run")
    @Log(title = title, businessType = BusinessType.EDIT)
    @GetMapping("/run/{jobId}")
    public AjaxResult run(@PathVariable Long jobId) throws SchedulerException {
        jobService.run(jobId);
        return AjaxResult.success();
    }

    /**
     * 删除定时任务
     */
    @RequiresPermissions("monitor:job:remove")
    @Log(title = title, businessType = BusinessType.DELETE)
    @GetMapping("/delete/{jobId}")
    public AjaxResult remove(@PathVariable Long jobId) throws SchedulerException {
        jobService.deleteJobById(jobId);
        return AjaxResult.success();
    }
}
