package com.platform.modules.tool.gen.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.common.utils.gen.constant.GenConstants;
import com.platform.common.core.CharsetKit;
import com.platform.common.exception.BaseException;
import com.platform.common.web.service.impl.BaseServiceImpl;
import com.platform.modules.tool.gen.dao.GenTableColumnDao;
import com.platform.modules.tool.gen.dao.GenTableDao;
import com.platform.modules.tool.gen.domain.GenTable;
import com.platform.modules.tool.gen.domain.GenTableColumn;
import com.platform.modules.tool.gen.service.GenTableColumnService;
import com.platform.modules.tool.gen.service.GenTableService;
import com.platform.modules.tool.gen.util.GenUtils;
import com.platform.modules.tool.gen.util.VelocityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 业务 服务层实现
 */
@Service
@Slf4j
public class GenTableServiceImpl extends BaseServiceImpl<GenTable> implements GenTableService {

    @Resource
    private GenTableDao tableDao;

    @Resource
    private GenTableColumnDao columnDao;

    @Resource
    private GenTableColumnService columnService;

    @Autowired
    public void setBaseDao() {
        super.setBaseDao(tableDao);
    }

    /**
     * 查询业务列表
     *
     * @param genTable 业务信息
     * @return 业务集合
     */
    @Override
    public List<GenTable> queryList(GenTable genTable) {
        return tableDao.queryList(genTable);
    }

    /**
     * 查询据库列表
     *
     * @param genTable 业务信息
     * @return 数据库表集合
     */
    public List<GenTable> queryDbTableList(GenTable genTable) {
        return tableDao.queryDbTableList(genTable);
    }


    /**
     * 查询据库列表
     *
     * @param tableNames 表名称组
     * @return 数据库表集合
     */
    public List<GenTable> queryDbTableListByNames(String[] tableNames) {
        return tableDao.queryDbTableListByNames(tableNames);
    }

    /**
     * 查询业务信息
     *
     * @param id 业务ID
     * @return 业务信息
     */
    @Override
    public GenTable getInfo(Long id) {
        GenTable genTable = getById(id);
        this.setTableFromOptions(genTable);
        return genTable;
    }

    /**
     * 设置代码生成其他选项值
     *
     * @param genTable 设置后的生成对象
     */
    private void setTableFromOptions(GenTable genTable) {
        JSONObject paramsObj = JSONUtil.parseObj(genTable.getOptions());
        if (paramsObj != null) {
            String treeCode = paramsObj.getStr(GenConstants.TREE_CODE);
            String treeParentCode = paramsObj.getStr(GenConstants.TREE_PARENT_CODE);
            String treeName = paramsObj.getStr(GenConstants.TREE_NAME);
            genTable.setTreeCode(treeCode);
            genTable.setTreeParentCode(treeParentCode);
            genTable.setTreeName(treeName);
        }
    }

    /**
     * 修改业务
     *
     * @param genTable 业务信息
     * @return 结果
     */
    @Override
    @Transactional
    public void updateGenTable(GenTable genTable) {
        String options = JSONUtil.toJsonStr(genTable.getParams());
        genTable.setOptions(options);
        int row = updateById(genTable);
        if (row > 0) {
            for (GenTableColumn cenTableColumn : genTable.getColumns()) {
                columnService.updateById(cenTableColumn);
            }
        }
    }

    /**
     * 删除业务对象
     *
     * @return 结果
     */
    @Override
    @Transactional
    public void deleteGenTable(Long[] tableIds) {
        this.deleteByIds(tableIds);
        columnService.deleteByTableIds(tableIds);
    }

    /**
     * 导入表结构
     *
     * @param tableList 导入表列表
     */
    @Override
    @Transactional
    public void importGenTable(List<GenTable> tableList) {
        for (GenTable table : tableList) {
            try {
                String tableName = table.getTableName();
                GenUtils.initTable(table);
                int row = this.add(table);
                if (row > 0) {
                    // 保存列信息
                    List<GenTableColumn> genTableColumns = columnDao.queryByTableName(tableName);
                    for (GenTableColumn column : genTableColumns) {
                        GenUtils.initColumnField(column, table);
                        columnService.add(column);
                    }
                }
            } catch (Exception e) {
                log.error("表名 " + table.getTableName() + " 导入失败：", e);
            }
        }
    }

    /**
     * 预览代码
     *
     * @param tableId 表编号
     * @return 预览数据列表
     */
    public Map<String, String> previewCode(Long tableId) {
        Map<String, String> dataMap = new LinkedHashMap<>();
        // 查询表信息
        GenTable table = this.getById(tableId);
        // 拼接数据
        table.setColumns(columnService.queryByTableId(tableId));
        // 查询列信息
        List<GenTableColumn> columns = table.getColumns();
        setPkColumn(table, columns);
        // 初始化vm方法
        initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, CharsetKit.UTF_8);
            tpl.merge(context, sw);
            dataMap.put(template, sw.toString());
        }
        return dataMap;
    }

    /**
     * 初始化vm方法
     */
    private static void initVelocity() {
        Properties p = new Properties();
        try {
            // 加载classpath目录下的vm文件
            p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            // 定义字符集
            p.setProperty(Velocity.ENCODING_DEFAULT, CharsetKit.UTF_8);
            p.setProperty(Velocity.OUTPUT_ENCODING, CharsetKit.UTF_8);
            // 初始化Velocity引擎，指定配置Properties
            Velocity.init(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成代码
     *
     * @param tableName 表名称
     * @return 数据
     */
    @Override
    public byte[] generatorCode(String tableName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        generatorCode(tableName, zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 批量生成代码
     *
     * @param tableNames 表数组
     * @return 数据
     */
    @Override
    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            generatorCode(tableName, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 查询表信息并生成代码
     */
    private void generatorCode(String tableName, ZipOutputStream zip) {
        // 查询表信息
        GenTable genTable = new GenTable();
        genTable.setTableName(tableName);
        GenTable table = tableDao.selectOne(new QueryWrapper<>(genTable));
        // 拼接数据
        table.setColumns(columnService.queryByTableId(table.getTableId()));
        // 查询列信息
        List<GenTableColumn> columns = table.getColumns();
        setPkColumn(table, columns);

        // 初始化vm方法
        initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, CharsetKit.UTF_8);
            tpl.merge(context, sw);
            try {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
                IOUtils.write(sw.toString(), zip, CharsetKit.UTF_8);
                IOUtils.closeQuietly(sw);
                zip.flush();
                zip.closeEntry();
            } catch (IOException e) {
                log.error("渲染模板失败，表名：" + table.getTableName(), e);
            }
        }
    }

    /**
     * 修改保存参数校验
     *
     * @param genTable 业务信息
     */
    @Override
    public void validateEdit(GenTable genTable) {
        if (GenConstants.TPL_TREE.equals(genTable.getTplCategory())) {
            String options = JSONUtil.toJsonStr(genTable.getParams());
            JSONObject paramsObj = JSONUtil.parseObj(options);
            if (StringUtils.isEmpty(paramsObj.getStr(GenConstants.TREE_CODE))) {
                throw new BaseException("树编码字段不能为空");
            } else if (StringUtils.isEmpty(paramsObj.getStr(GenConstants.TREE_PARENT_CODE))) {
                throw new BaseException("树父编码字段不能为空");
            } else if (StringUtils.isEmpty(paramsObj.getStr(GenConstants.TREE_NAME))) {
                throw new BaseException("树名称字段不能为空");
            }
        }
    }

    /**
     * 设置主键列信息
     *
     * @param columns 业务字段列表
     */
    private void setPkColumn(GenTable table, List<GenTableColumn> columns) {
        for (GenTableColumn column : columns) {
            if (column.isPk()) {
                table.setPkColumn(column);
                break;
            }
        }
        if (table.getPkColumn() == null) {
            table.setPkColumn(columns.get(0));
        }
    }

}