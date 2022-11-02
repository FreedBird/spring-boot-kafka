package com.platform.modules.sys.controller;

import com.platform.common.aspectj.lang.annotation.Log;
import com.platform.common.aspectj.lang.enums.BusinessType;
import com.platform.common.constant.Constants;
import com.platform.common.web.controller.BaseController;
import com.platform.common.web.domain.AjaxResult;
import com.platform.common.web.page.TableDataInfo;
import com.platform.modules.sys.domain.SysDictType;
import com.platform.modules.sys.service.SysDictTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典信息
 */
@RestController
@RequestMapping("/sys/dict/type")
public class SysDictTypeController extends BaseController {

    private final static String title = "字典类型";

    @Autowired
    private SysDictTypeService dictTypeService;

    @RequiresPermissions("sys:dict:list")
    @GetMapping("/list")
    public TableDataInfo list(SysDictType dictType) {
        startPage();
        List<SysDictType> list = dictTypeService.queryList(dictType);
        return getDataTable(list);
    }

    /**
     * 查询字典类型详细
     */
    @RequiresPermissions("sys:dict:query")
    @GetMapping("/info/{dictId}")
    public AjaxResult getInfo(@PathVariable Long dictId) {
        return AjaxResult.success(dictTypeService.getById(dictId));
    }

    /**
     * 新增字典类型
     */
    @RequiresPermissions("sys:dict:add")
    @Log(title = title, businessType = BusinessType.ADD)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody SysDictType dict) {
        return toAjax(dictTypeService.addDictType(dict));
    }

    /**
     * 修改字典类型
     */
    @RequiresPermissions("sys:dict:edit")
    @Log(title = title, businessType = BusinessType.EDIT)
    @PostMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody SysDictType dict) {
        return toAjax(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */
    @RequiresPermissions("sys:dict:remove")
    @Log(title = title, businessType = BusinessType.DELETE)
    @GetMapping("/delete/{dictId}")
    public AjaxResult remove(@PathVariable Long dictId) {
        return toAjax(dictTypeService.deleteDictType(dictId));
    }

    /**
     * 获取字典选择框列表
     */
    @RequiresPermissions(Constants.PERM_SYS)
    @GetMapping("/dictTypeList")
    public AjaxResult dictTypeList(SysDictType dictType) {
        List<SysDictType> dictTypes = dictTypeService.queryList(dictType);
        return AjaxResult.success(dictTypes);
    }

}
