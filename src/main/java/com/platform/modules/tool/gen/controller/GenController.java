package com.platform.modules.tool.gen.controller;

import cn.hutool.core.convert.Convert;
import com.platform.common.aspectj.lang.annotation.Log;
import com.platform.common.aspectj.lang.enums.BusinessType;
import com.platform.common.web.controller.BaseController;
import com.platform.common.web.domain.AjaxResult;
import com.platform.common.web.page.TableDataInfo;
import com.platform.modules.tool.gen.domain.GenTable;
import com.platform.modules.tool.gen.domain.GenTableColumn;
import com.platform.modules.tool.gen.service.GenTableColumnService;
import com.platform.modules.tool.gen.service.GenTableService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成 操作处理
 */
@RestController
@RequestMapping("/tool/gen")
public class GenController extends BaseController {

    private final static String title = "代码生成";

    @Resource
    private GenTableService genTableService;

    @Resource
    private GenTableColumnService genTableColumnService;

    /**
     * 查询代码生成列表
     */
    @RequiresPermissions("tool:gen:list")
    @GetMapping("/list")
    public TableDataInfo genList(GenTable genTable) {
        startPage();
        List<GenTable> list = genTableService.queryList(genTable);
        return getDataTable(list);
    }

    /**
     * 修改代码生成业务
     */
    @RequiresPermissions("tool:gen:query")
    @GetMapping("/info/{tableId}")
    public AjaxResult getInfo(@PathVariable Long tableId) {
        GenTable table = genTableService.getInfo(tableId);
        List<GenTableColumn> list = genTableColumnService.queryByTableId(tableId);
        Map<String, Object> map = new HashMap<>();
        map.put("info", table);
        map.put("rows", list);
        return AjaxResult.success(map);
    }

    /**
     * 查询数据库列表
     */
    @RequiresPermissions("tool:gen:list")
    @GetMapping("/db/list")
    public TableDataInfo dataList(GenTable genTable) {
        startPage();
        List<GenTable> list = genTableService.queryDbTableList(genTable);
        return getDataTable(list);
    }

    /**
     * 查询数据表字段列表
     */
    @RequiresPermissions("tool:gen:list")
    @GetMapping("/column/{tableId}")
    public TableDataInfo columnList(@PathVariable Long tableId) {
        TableDataInfo dataInfo = new TableDataInfo();
        List<GenTableColumn> list = genTableColumnService.queryByTableId(tableId);
        dataInfo.setRows(list);
        dataInfo.setTotal(list.size());
        return dataInfo;
    }

    /**
     * 导入表结构（保存）
     */
    @RequiresPermissions("tool:gen:list")
    @Log(title = title, businessType = BusinessType.IMPORT)
    @PostMapping("/importTable")
    public AjaxResult importTableSave(String tables) {
        String[] tableNames = Convert.toStrArray(tables);
        // 查询表信息
        List<GenTable> tableList = genTableService.queryDbTableListByNames(tableNames);
        genTableService.importGenTable(tableList);
        return AjaxResult.success();
    }

    /**
     * 修改保存代码生成业务
     */
    @RequiresPermissions("tool:gen:edit")
    @Log(title = title, businessType = BusinessType.EDIT)
    @PostMapping("/edit")
    public AjaxResult editSave(@Validated @RequestBody GenTable genTable) {
        genTableService.validateEdit(genTable);
        genTableService.updateGenTable(genTable);
        return AjaxResult.success();
    }

    /**
     * 删除代码生成
     */
    @RequiresPermissions("tool:gen:remove")
    @Log(title = title, businessType = BusinessType.DELETE)
    @GetMapping("/delete/{tableIds}")
    public AjaxResult remove(@PathVariable Long[] tableIds) {
        genTableService.deleteGenTable(tableIds);
        return AjaxResult.success();
    }

    /**
     * 预览代码
     */
    @RequiresPermissions("tool:gen:preview")
    @GetMapping("/preview/{tableId}")
    public AjaxResult preview(@PathVariable("tableId") Long tableId) {
        Map<String, String> dataMap = genTableService.previewCode(tableId);
        return AjaxResult.success(dataMap);
    }

    /**
     * 生成代码
     */
    @RequiresPermissions("tool:gen:code")
    @Log(title = title, businessType = BusinessType.CODE)
    @GetMapping("/genCode/{tableName}")
    public void genCode(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
        byte[] data = genTableService.generatorCode(tableName);
        genCode(response, data);
    }

    /**
     * 批量生成代码
     */
    @RequiresPermissions("tool:gen:code")
    @Log(title = title, businessType = BusinessType.CODE)
    @GetMapping("/batchGenCode")
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException {
        String[] tableNames = Convert.toStrArray(tables);
        byte[] data = genTableService.generatorCode(tableNames);
        genCode(response, data);
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}