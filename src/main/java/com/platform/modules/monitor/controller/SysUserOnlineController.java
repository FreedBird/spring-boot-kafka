package com.platform.modules.monitor.controller;

import com.platform.common.aspectj.lang.annotation.Log;
import com.platform.common.aspectj.lang.enums.BusinessType;
import com.platform.common.web.controller.BaseController;
import com.platform.common.web.domain.AjaxResult;
import com.platform.common.web.page.PageDomain;
import com.platform.common.web.page.TableDataInfo;
import com.platform.modules.sys.service.SysUserOnlineService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在线用户监控
 */
@RestController
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController {

    private final static String title = "在线用户";

    @Autowired
    private SysUserOnlineService userOnlineService;

    @RequiresPermissions("monitor:online:list")
    @GetMapping("/list")
    public TableDataInfo list(String userName) {
        PageDomain pageDomain = startPageDomain();
        return userOnlineService.querySysUser(userName, pageDomain);
    }

    /**
     * 强退用户
     */
    @RequiresPermissions("monitor:online:forceLogout")
    @Log(title = title, businessType = BusinessType.DELETE)
    @GetMapping("/logout/{tokenId}")
    public AjaxResult logout(@PathVariable String tokenId) {
        userOnlineService.logout(tokenId);
        return AjaxResult.success();
    }
}
