package com.platform.modules.sys.controller;

import com.platform.common.aspectj.lang.annotation.Log;
import com.platform.common.aspectj.lang.enums.BusinessType;
import com.platform.common.constant.Constants;
import com.platform.common.web.controller.BaseController;
import com.platform.common.web.domain.AjaxResult;
import com.platform.common.web.page.TableDataInfo;
import com.platform.modules.sys.domain.SysDictData;
import com.platform.modules.sys.service.SysDictDataService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典信息
 */
@RestController
@RequestMapping("/sys/dict/data")
public class SysDictDataController extends BaseController {

    private final static String title = "字典数据";

    @Autowired
    private SysDictDataService dictDataService;

    @RequiresPermissions("sys:dict:list")
    @GetMapping("/list")
    public TableDataInfo list(SysDictData dictData) {
        startPage();
        List<SysDictData> list = dictDataService.queryList(dictData);
        return getDataTable(list);
    }

    @Log(title = title, businessType = BusinessType.EXPORT)
    @RequiresPermissions("sys:dict:export")
    @GetMapping("/export")
    public AjaxResult export(SysDictData dictData) {
        List<SysDictData> list = dictDataService.queryList(dictData);
//        ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
//        return util.exportExcel(list, "字典数据");
        return null;
    }

    /**
     * 查询字典数据详细
     */
    @RequiresPermissions("sys:dict:query")
    @GetMapping("/info/{dictCode}")
    public AjaxResult getInfo(@PathVariable Long dictCode) {
        return AjaxResult.success(dictDataService.getById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @RequiresPermissions(Constants.PERM_SYS)
    @GetMapping("/dictType/{dictType}")
    public AjaxResult dictType(@PathVariable String dictType) {
        return AjaxResult.success(dictDataService.queryByType(dictType));
    }

    /**
     * 新增字典类型
     */
    @RequiresPermissions("sys:dict:add")
    @Log(title = title, businessType = BusinessType.ADD)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody SysDictData dict) {
        return toAjax(dictDataService.add(dict));
    }

    /**
     * 修改保存字典类型
     */
    @RequiresPermissions("sys:dict:edit")
    @Log(title = title, businessType = BusinessType.EDIT)
    @PostMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody SysDictData dict) {
        return toAjax(dictDataService.updateById(dict));
    }

    /**
     * 删除字典类型
     */
    @RequiresPermissions("sys:dict:remove")
    @Log(title = title, businessType = BusinessType.DELETE)
    @GetMapping("/delete/{dictCodes}")
    public AjaxResult remove(@PathVariable Long[] dictCodes) {
        return toAjax(dictDataService.deleteByIds(dictCodes));
    }
}
