package com.platform.modules.sys.controller;

import com.platform.common.aspectj.lang.annotation.Log;
import com.platform.common.aspectj.lang.enums.BusinessType;
import com.platform.common.constant.Constants;
import com.platform.common.shiro.utils.Md5Utils;
import com.platform.common.utils.validation.ValidateGroup;
import com.platform.common.web.controller.BaseController;
import com.platform.common.web.domain.AjaxResult;
import com.platform.modules.sys.domain.SysUser;
import com.platform.modules.sys.service.SysUserService;
import com.platform.modules.sys.vo.LoginBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 个人信息 业务处理
 */
@RestController
@RequestMapping("/sys/profile")
@Slf4j
public class SysProfileController extends BaseController {

    private final static String title = "个人信息";

    @Autowired
    private SysUserService userService;

    /**
     * 个人信息
     */
    @RequiresPermissions(Constants.PERM_SYS)
    @GetMapping("/info")
    public AjaxResult profile() {
        SysUser sysUser = userService.getById(getSysUserId());
        AjaxResult ajax = AjaxResult.success(sysUser)
                .put("roleName", getSysRole().getRoleName());
        return ajax;
    }

    /**
     * 修改用户
     */
    @RequiresPermissions(Constants.PERM_SYS)
    @Log(title = title, businessType = BusinessType.EDIT)
    @PostMapping("/edit")
    public AjaxResult updateProfile(@RequestBody SysUser user) {
        return AjaxResult.success(userService.updateProfile(user));
    }

    /**
     * 修改密码
     */
    @RequiresPermissions(Constants.PERM_SYS)
    @Log(title = "修改密码", businessType = BusinessType.EDIT)
    @PostMapping("/updatePwd")
    public AjaxResult updatePwd(@Validated(ValidateGroup.PASSWORD.class) @RequestBody LoginBody loginBody) {
        String oldPassword = loginBody.getPassword();
        String newPassword = loginBody.getNewPassword();
        if (oldPassword.equals(newPassword)) {
            return AjaxResult.fail("新旧密码不能相同");
        }
        SysUser sysUser = userService.getById(getSysUserId());
        // 检验旧密码
        if (!sysUser.getPassword().equals(Md5Utils.credentials(oldPassword, sysUser.getSalt()))) {
            return AjaxResult.fail("旧密码不正确");
        }
        userService.updatePwd(sysUser.getUserId(), newPassword);
        return AjaxResult.successMsg("密码修改成功");
    }

    /**
     * 头像上传
     */
    @RequiresPermissions(Constants.PERM_SYS)
    @Log(title = "用户头像", businessType = BusinessType.EDIT)
    @PostMapping("/avatar")
    public AjaxResult avatar(String path) {
        userService.updateAvatar(getSysUserId(), path);
        return AjaxResult.success(path);
    }
}
