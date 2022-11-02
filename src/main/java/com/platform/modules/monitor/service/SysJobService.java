package com.platform.modules.monitor.service;

import com.platform.common.web.service.BaseService;
import com.platform.common.utils.job.exception.TaskException;
import com.platform.modules.monitor.domain.SysJob;
import org.quartz.SchedulerException;

/**
 * 定时任务调度信息信息 服务层
 */
public interface SysJobService extends BaseService<SysJob> {

    /**
     * 暂停任务
     *
     * @param job 调度信息
     * @return 结果
     */
    Integer pauseJob(SysJob job) throws SchedulerException;

    /**
     * 恢复任务
     *
     * @param job 调度信息
     * @return 结果
     */
    Integer resumeJob(SysJob job) throws SchedulerException;

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param job 调度信息
     * @return 结果
     */
    Integer deleteJob(SysJob job) throws SchedulerException;

    /**
     * 批量删除调度信息
     *
     * @param jobId 需要删除的任务ID
     * @return 结果
     */
    void deleteJobById(Long jobId) throws SchedulerException;

    /**
     * 任务调度状态修改
     *
     * @param job 调度信息
     * @return 结果
     */
    Integer changeStatus(SysJob job) throws SchedulerException;

    /**
     * 立即运行任务
     *
     * @param jobId 调度信息
     * @return 结果
     */
    void run(Long jobId) throws SchedulerException;

    /**
     * 新增任务
     *
     * @param job 调度信息
     * @return 结果
     */
    Integer addJob(SysJob job) throws SchedulerException, TaskException;

    /**
     * 更新任务
     *
     * @param job 调度信息
     * @return 结果
     */
    Integer updateJob(SysJob job) throws SchedulerException, TaskException;
}