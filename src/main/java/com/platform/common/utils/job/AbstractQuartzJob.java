package com.platform.common.utils.job;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.platform.common.utils.job.constant.ScheduleConstants;
import com.platform.common.enums.YesOrNoEnum;
import com.platform.modules.monitor.domain.SysJob;
import com.platform.modules.monitor.domain.SysJobLog;
import com.platform.modules.monitor.service.SysJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.BeanUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * 抽象quartz调用
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {

    /**
     * 线程本地变量
     */
    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) {
        SysJob sysJob = new SysJob();
        Object object = context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES);
        BeanUtils.copyProperties(object, sysJob);
        try {
            before();
            if (sysJob != null) {
                doExecute(context, sysJob);
            }
            after(sysJob, null);
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            after(sysJob, e);
        }
    }

    /**
     * 执行前
     */
    protected void before() {
        threadLocal.set(DateUtil.date());
    }

    /**
     * 执行后
     *
     * @param sysJob 系统计划任务
     */
    protected void after(SysJob sysJob, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        final SysJobLog sysJobLog = new SysJobLog();
        sysJobLog.setJobName(sysJob.getJobName());
        sysJobLog.setJobGroup(sysJob.getJobGroup());
        sysJobLog.setInvokeTarget(sysJob.getInvokeTarget());
        sysJobLog.setCreateTime(startTime);
        long runMs = DateUtil.between(startTime, DateUtil.date(), DateUnit.MS);
        sysJobLog.setJobMessage(sysJobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null) {
            sysJobLog.setStatus(YesOrNoEnum.NO);
            String errorMsg = StrUtil.sub(getExceptionMessage(e), 0, 2000);
            sysJobLog.setExceptionInfo(errorMsg);
        } else {
            sysJobLog.setStatus(YesOrNoEnum.YES);
        }
        // 写入数据库当中
        SpringUtil.getBean(SysJobLogService.class).add(sysJobLog);
    }

    /**
     * 获取exception的详细错误信息。
     */
    private static String getExceptionMessage(Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return sw.toString();
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception;
}
