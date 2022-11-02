package com.platform.modules.sys.controller;

import cn.hutool.core.date.DateTime;
import com.platform.common.aspectj.lang.annotation.Log;
import com.platform.common.aspectj.lang.enums.BusinessType;
import com.platform.common.web.controller.BaseController;
import com.platform.common.web.domain.AjaxResult;
import com.platform.common.web.page.TableDataInfo;
import com.platform.modules.sys.domain.SysNotice;
import com.platform.modules.sys.service.SysNoticeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公告 信息操作处理
 */
@RestController
@RequestMapping("/sys/notice")
public class SysNoticeController extends BaseController {

    private final static String title = "通知公告";

    @Autowired
    private SysNoticeService noticeService;

    /**
     * 获取通知公告列表
     */
    @RequiresPermissions("sys:notice:list")
    @GetMapping("/list")
    public TableDataInfo list(SysNotice notice) {
        startPage();
        List<SysNotice> list = noticeService.queryList(notice);
        return getDataTable(list);
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @RequiresPermissions("sys:notice:query")
    @GetMapping("/info/{noticeId}")
    public AjaxResult getInfo(@PathVariable Long noticeId) {
        return AjaxResult.success(noticeService.getById(noticeId));
    }

    /**
     * 新增通知公告
     */
    @RequiresPermissions("sys:notice:add")
    @Log(title = title, businessType = BusinessType.ADD)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody SysNotice notice) {
        notice.setCreateTime(DateTime.now());
        return toAjax(noticeService.add(notice));
    }

    /**
     * 修改通知公告
     */
    @RequiresPermissions("sys:notice:edit")
    @Log(title = title, businessType = BusinessType.EDIT)
    @PostMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody SysNotice notice) {
        return toAjax(noticeService.updateById(notice));
    }

    /**
     * 删除通知公告
     */
    @RequiresPermissions("sys:notice:remove")
    @Log(title = title, businessType = BusinessType.DELETE)
    @GetMapping("/delete/{noticeId}")
    public AjaxResult remove(@PathVariable Long noticeId) {
        return toAjax(noticeService.deleteById(noticeId));
    }

}
