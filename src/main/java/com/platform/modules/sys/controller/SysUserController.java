package com.platform.modules.sys.controller;

import com.platform.common.aspectj.lang.annotation.Log;
import com.platform.common.aspectj.lang.enums.BusinessType;
import com.platform.common.constant.Constants;
import com.platform.common.web.controller.BaseController;
import com.platform.common.web.domain.AjaxResult;
import com.platform.common.web.page.TableDataInfo;
import com.platform.modules.sys.domain.SysRole;
import com.platform.modules.sys.domain.SysUser;
import com.platform.modules.sys.service.SysRoleService;
import com.platform.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户信息
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

    private final static String title = "用户管理";

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    /**
     * 获取用户列表
     */
    @RequiresPermissions("sys:user:list")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user) {
        startPage();
        List<SysUser> list = userService.queryListMore(user);
        return getDataTable(list);
    }

    @Log(title = title, businessType = BusinessType.EXPORT)
    @RequiresPermissions("sys:user:export")
    @GetMapping("/export")
    public AjaxResult export(SysUser user) {
        List<SysUser> list = userService.queryListMore(user);
//        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
//        return util.exportExcel(list, "用户数据");
        return null;
    }

    @Log(title = title, businessType = BusinessType.IMPORT)
    @RequiresPermissions("sys:user:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
//        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
//        List<SysUser> userList = util.importExcel(file.getInputStream());
//        String message = userService.importUser(userList, updateSupport);
//        return AjaxResult.successMsg(message);
        return null;
    }

    @RequiresPermissions(Constants.PERM_SYS)
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
//        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
//        return util.importTemplateExcel("用户数据");
        return null;
    }

    /**
     * 根据用户编号获取详细信息
     */
    @RequiresPermissions("sys:user:query")
    @GetMapping("/info/{userId}")
    public AjaxResult getInfo(@PathVariable Long userId) {
        return AjaxResult.success(userService.getById(userId));
    }

    /**
     * 新增用户
     */
    @RequiresPermissions("sys:user:add")
    @Log(title = title, businessType = BusinessType.ADD)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody SysUser user) {
        return toAjax(userService.addUser(user));
    }

    /**
     * 修改用户
     */
    @RequiresPermissions("sys:user:edit")
    @Log(title = title, businessType = BusinessType.EDIT)
    @PostMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody SysUser user) {
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @RequiresPermissions("sys:user:remove")
    @Log(title = title, businessType = BusinessType.DELETE)
    @GetMapping("/delete/{userId}")
    public AjaxResult remove(@PathVariable Long userId) {
        return toAjax(userService.deleteUser(userId));
    }

    /**
     * 重置密码
     */
    @RequiresPermissions("sys:user:edit")
    @Log(title = title, businessType = BusinessType.EDIT)
    @PostMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user) {
        userService.updatePwd(user.getUserId(), user.getPassword());
        return AjaxResult.successMsg("重置成功");
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("sys:user:edit")
    @Log(title = title, businessType = BusinessType.EDIT)
    @PostMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user) {
        return toAjax(userService.changeStatus(user));
    }

    /**
     * 获取角色选择框列表
     */
    @RequiresPermissions("sys:user:list")
    @GetMapping("/roleList")
    public AjaxResult roleList(SysRole role) {
        List<SysRole> list = roleService.queryList(role);
        return AjaxResult.success(list);
    }
}