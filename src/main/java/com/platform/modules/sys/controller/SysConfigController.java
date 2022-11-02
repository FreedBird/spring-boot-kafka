package com.platform.modules.sys.controller;

import com.platform.common.aspectj.lang.annotation.Log;
import com.platform.common.aspectj.lang.enums.BusinessType;
import com.platform.common.constant.Constants;
import com.platform.common.web.controller.BaseController;
import com.platform.common.web.domain.AjaxResult;
import com.platform.common.web.page.TableDataInfo;
import com.platform.modules.sys.domain.SysConfig;
import com.platform.modules.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 参数配置 信息操作处理
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends BaseController {

    private final static String title = "参数管理";

    @Resource
    private SysConfigService configService;

    /**
     * 获取参数配置列表
     */
    @RequiresPermissions("sys:config:list")
    @GetMapping("/list")
    public TableDataInfo list(SysConfig config) {
        startPage();
        List<SysConfig> list = configService.queryList(config);
        return getDataTable(list);
    }

    /**
     * 根据参数编号获取详细信息
     */
    @RequiresPermissions("sys:config:query")
    @GetMapping("/info/{configId}")
    public AjaxResult getInfo(@PathVariable Long configId) {
        return AjaxResult.success(configService.getById(configId));
    }

    /**
     * 根据参数键名查询参数值
     */
    @RequiresPermissions(Constants.PERM_SYS)
    @GetMapping("/configKey/{configKey}")
    public AjaxResult getConfigKey(@PathVariable String configKey) {
        return AjaxResult.success(configService.quertConfigByKey(configKey));
    }

    /**
     * 新增参数配置
     */
    @RequiresPermissions("sys:config:add")
    @Log(title = "参数管理", businessType = BusinessType.ADD)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody SysConfig config) {
        return toAjax(configService.addConfig(config));
    }

    /**
     * 修改参数配置
     */
    @RequiresPermissions("sys:config:edit")
    @Log(title = "参数管理", businessType = BusinessType.EDIT)
    @PostMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody SysConfig config) {
        return toAjax(configService.updConfig(config));
    }

    /**
     * 删除参数配置
     */
    @RequiresPermissions("sys:config:remove")
    @Log(title = "参数管理", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{configId}")
    public AjaxResult remove(@PathVariable Long configId) {
        return toAjax(configService.deleteById(configId));
    }
}
