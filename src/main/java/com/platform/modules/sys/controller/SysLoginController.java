package com.platform.modules.sys.controller;

import com.platform.common.config.CaptchaConfig;
import com.platform.common.constant.Constants;
import com.platform.common.enums.YesOrNoEnum;
import com.platform.common.exception.LoginException;
import com.platform.common.shiro.ShiroLoginAuth;
import com.platform.common.shiro.ShiroUtils;
import com.platform.common.shiro.enums.ShiroUserTypeEnum;
import com.platform.common.utils.LogUtils;
import com.platform.common.utils.validation.ValidateGroup;
import com.platform.common.web.controller.BaseController;
import com.platform.common.web.domain.AjaxResult;
import com.platform.modules.sys.domain.SysUser;
import com.platform.modules.sys.service.SysMenuService;
import com.platform.modules.sys.service.SysTokenService;
import com.platform.modules.sys.service.SysUserService;
import com.platform.modules.sys.vo.LoginBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录验证
 */
@RestController
@Slf4j
public class SysLoginController extends BaseController {

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private CaptchaConfig captchaConfig;

    @Autowired
    private SysTokenService tokenService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录方法
     *
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@Validated(ValidateGroup.LOGIN.class) @RequestBody LoginBody loginBody) {
        String username = loginBody.getUsername();
        String password = loginBody.getPassword();
        String msg = null;
        try {
            ShiroLoginAuth auth = new ShiroLoginAuth(username, password, ShiroUserTypeEnum.SYS_USER);
            ShiroUtils.getSubject().login(auth);
        } catch (LoginException e) {
            msg = e.getMessage();
        } catch (AuthenticationException e) {
            msg = "账号或密码不正确";
        } catch (Exception e) {
            msg = "未知异常";
            log.error(msg, e);
        }
        if (!StringUtils.isEmpty(msg)) {
            LogUtils.recordLogin(username, YesOrNoEnum.NO, msg);
            return AjaxResult.fail(msg);
        }
        LogUtils.recordLogin(username, YesOrNoEnum.YES, "登录成功");
        // 生成TOKEN
        String tokenStr = tokenService.generateToken(ShiroUserTypeEnum.SYS_USER);
        return AjaxResult.success().put("token", tokenStr);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @RequiresPermissions(Constants.PERM_SYS)
    @GetMapping("/getInfo")
    public AjaxResult getInfo() {
        AjaxResult ajax = AjaxResult.success();
        SysUser user = sysUserService.getById(getSysUserId());
        ajax.put("user", user);
        ajax.put("role", getSysRole().getRoleKey());
        ajax.put("permissions", menuService.queryPerms(user.getRoleId()));
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @RequiresPermissions(Constants.PERM_SYS)
    @GetMapping("/getRouters")
    public AjaxResult getRouters() {
        return AjaxResult.success(menuService.getRouters());
    }

    /**
     * 生成验证码
     */
    @GetMapping("/getCaptcha")
    public AjaxResult getCaptcha() {
        return AjaxResult.success(captchaConfig.easyCaptcha());
    }

    /**
     * 退出系统
     */
    @RequiresPermissions(Constants.PERM_SYS)
    @GetMapping("/logout")
    public AjaxResult logout() {
        String username = getSysUser().getUserName();
        try {
            tokenService.deleteToken(ShiroUtils.getLoginUser().getTokenId(), ShiroUserTypeEnum.SYS_USER);
            ShiroUtils.getSubject().logout();
            log.info("退出成功。。。。");
        } catch (Exception ex) {
            log.error("退出异常", ex);
        } finally {
            LogUtils.recordLogin(username, YesOrNoEnum.YES, "退出成功");
        }
        return AjaxResult.successMsg("退出成功");
    }
}
