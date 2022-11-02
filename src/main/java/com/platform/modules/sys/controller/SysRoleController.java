package com.platform.modules.sys.controller;

import com.platform.common.aspectj.lang.annotation.Log;
import com.platform.common.aspectj.lang.enums.BusinessType;
import com.platform.common.web.controller.BaseController;
import com.platform.common.web.domain.AjaxResult;
import com.platform.common.web.page.TableDataInfo;
import com.platform.modules.sys.domain.SysRole;
import com.platform.modules.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色信息
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {

    private final static String title = "角色管理";

    @Autowired
    private SysRoleService roleService;

    @RequiresPermissions("sys:role:list")
    @GetMapping("/list")
    public TableDataInfo list(SysRole role) {
        startPage();
        List<SysRole> list = roleService.queryList(role);
        return getDataTable(list);
    }

    /**
     * 根据角色编号获取详细信息
     */
    @RequiresPermissions("sys:role:query")
    @GetMapping("/info/{roleId}")
    public AjaxResult getInfo(@PathVariable Long roleId) {
        return AjaxResult.success(roleService.getById(roleId));
    }

    /**
     * 新增角色
     */
    @RequiresPermissions("sys:role:add")
    @Log(title = title, businessType = BusinessType.ADD)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody SysRole role) {
        return toAjax(roleService.addRole(role));
    }

    /**
     * 修改保存角色
     */
    @RequiresPermissions("sys:role:edit")
    @Log(title = title, businessType = BusinessType.EDIT)
    @PostMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody SysRole role) {
        return toAjax(roleService.updateRole(role));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("sys:role:edit")
    @Log(title = title, businessType = BusinessType.EDIT)
    @PostMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysRole role) {
        return toAjax(roleService.changeStatus(role));
    }

    /**
     * 删除角色
     */
    @RequiresPermissions("sys:role:remove")
    @Log(title = title, businessType = BusinessType.DELETE)
    @GetMapping("/delete/{roleId}")
    public AjaxResult remove(@PathVariable Long roleId) {
        return toAjax(roleService.deleteRole(roleId));
    }

}