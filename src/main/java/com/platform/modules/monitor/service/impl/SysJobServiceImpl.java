package com.platform.modules.monitor.service.impl;

import com.platform.common.enums.YesOrNoEnum;
import com.platform.common.web.service.impl.BaseServiceImpl;
import com.platform.common.utils.job.constant.ScheduleConstants;
import com.platform.common.utils.job.exception.TaskException;
import com.platform.common.utils.job.ScheduleUtils;
import com.platform.modules.monitor.dao.SysJobDao;
import com.platform.modules.monitor.domain.SysJob;
import com.platform.modules.monitor.service.SysJobService;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务调度信息 服务层
 */
@Service
public class SysJobServiceImpl extends BaseServiceImpl<SysJob> implements SysJobService {

    @Resource
    private Scheduler scheduler;

    @Resource
    private SysJobDao jobDao;

    @Autowired
    public void setBaseDao() {
        super.setBaseDao(jobDao);
    }

    @Override
    public List<SysJob> queryList(SysJob sysJob) {
        return jobDao.queryList(sysJob);
    }

    /**
     * 项目启动时，初始化定时器 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void init() throws SchedulerException, TaskException {
        scheduler.clear();
        List<SysJob> jobList = jobDao.queryList(new SysJob());
        for (SysJob job : jobList) {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
    }

    /**
     * 暂停任务
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public Integer pauseJob(SysJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(YesOrNoEnum.NO);
        Integer result = jobDao.updateById(job);
        if (result > 0) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return result;
    }

    /**
     * 恢复任务
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public Integer resumeJob(SysJob job) throws SchedulerException {
        String jobGroup = job.getJobGroup();
        job.setStatus(YesOrNoEnum.YES);
        Integer result = jobDao.updateById(job);
        if (result > 0) {
            scheduler.resumeJob(ScheduleUtils.getJobKey(job.getJobId(), jobGroup));
        }
        return result;
    }

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public Integer deleteJob(SysJob job) throws SchedulerException {
        String jobGroup = job.getJobGroup();
        Integer result = jobDao.deleteById(job);
        if (result > 0) {
            scheduler.deleteJob(ScheduleUtils.getJobKey(job.getJobId(), jobGroup));
        }
        return result;
    }

    /**
     * 批量删除调度信息
     *
     * @param jobId 需要删除的任务ID
     * @return 结果
     */
    @Override
    @Transactional
    public void deleteJobById(Long jobId) throws SchedulerException {
        SysJob job = this.getById(jobId);
        deleteJob(job);
    }

    /**
     * 任务调度状态修改
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public Integer changeStatus(SysJob job) throws SchedulerException {
        Integer result = 0;
        if (YesOrNoEnum.YES.equals(job.getStatus())) {
            result = resumeJob(job);
        } else if (YesOrNoEnum.NO.equals(job.getStatus())) {
            result = pauseJob(job);
        }
        return result;
    }

    /**
     * 立即运行任务
     *
     * @param jobId 调度信息
     */
    @Override
    @Transactional
    public void run(Long jobId) throws SchedulerException {
        SysJob job = this.getById(jobId);
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, job);
        scheduler.triggerJob(ScheduleUtils.getJobKey(job.getJobId(), job.getJobGroup()), dataMap);
    }

    /**
     * 新增任务
     *
     * @param job 调度信息 调度信息
     */
    @Override
    @Transactional
    public Integer addJob(SysJob job) throws SchedulerException, TaskException {
        Integer result = add(job);
        if (result > 0) {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
        return result;
    }

    /**
     * 更新任务的时间表达式
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public Integer updateJob(SysJob job) throws SchedulerException, TaskException {
        SysJob properties = getById(job.getJobId());
        Integer result = jobDao.updateById(job);
        if (result > 0) {
            updateSchedulerJob(job, properties.getJobGroup());
        }
        return result;
    }

    /**
     * 更新任务
     *
     * @param job      任务对象
     * @param jobGroup 任务组名
     */
    public void updateSchedulerJob(SysJob job, String jobGroup) throws SchedulerException, TaskException {
        Long jobId = job.getJobId();
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, job);
    }

}