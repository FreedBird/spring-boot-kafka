package com.platform.modules.sys.controller;

import com.platform.common.aspectj.lang.annotation.Log;
import com.platform.common.aspectj.lang.enums.BusinessType;
import com.platform.common.constant.Constants;
import com.platform.common.web.controller.BaseController;
import com.platform.common.web.domain.AjaxResult;
import com.platform.modules.sys.domain.SysMenu;
import com.platform.modules.sys.service.SysMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单信息
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {

    private final static String title = "菜单管理";

    @Resource
    private SysMenuService menuService;

    /**
     * 获取菜单列表
     */
    @RequiresPermissions("sys:menu:list")
    @GetMapping("/list")
    public AjaxResult list(SysMenu menu) {
        menu.setRoleId(getSysRole().getRoleId());
        List<SysMenu> menus = menuService.queryList(menu);
        return AjaxResult.success(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @RequiresPermissions("sys:menu:query")
    @GetMapping("/info/{menuId}")
    public AjaxResult getInfo(@PathVariable Long menuId) {
        return AjaxResult.success(menuService.getById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @RequiresPermissions(Constants.PERM_SYS)
    @GetMapping("/menuTree")
    public AjaxResult menuTree(SysMenu menu) {
        menu.setRoleId(getSysRole().getRoleId());
        return AjaxResult.success(menuService.getMenuTree(menu));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @RequiresPermissions(Constants.PERM_SYS)
    @GetMapping("/roleMenuTree/{roleId}")
    public AjaxResult roleMenuTree(@PathVariable("roleId") Long roleId) {
        SysMenu menu = new SysMenu();
        menu.setRoleId(roleId);
        return AjaxResult.success().put("checkedKeys", menuService.queryListByRoleId(roleId))
                .put("menus", menuService.getMenuTree(menu));
    }

    /**
     * 新增菜单
     */
    @RequiresPermissions("sys:menu:add")
    @Log(title = title, businessType = BusinessType.ADD)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody SysMenu menu) {
        return toAjax(menuService.addMenu(menu));
    }

    /**
     * 修改菜单
     */
    @RequiresPermissions("sys:menu:edit")
    @Log(title = title, businessType = BusinessType.EDIT)
    @PostMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody SysMenu menu) {
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
    @RequiresPermissions("sys:menu:remove")
    @Log(title = title, businessType = BusinessType.DELETE)
    @GetMapping("/delete/{menuId}")
    public AjaxResult remove(@PathVariable("menuId") Long menuId) {
        return toAjax(menuService.deleteMenu(menuId));
    }
}