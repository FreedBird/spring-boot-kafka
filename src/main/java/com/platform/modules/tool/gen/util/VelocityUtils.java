package com.platform.modules.tool.gen.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.common.utils.gen.constant.GenConstants;
import com.platform.modules.tool.gen.domain.GenTable;
import com.platform.modules.tool.gen.domain.GenTableColumn;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class VelocityUtils {
    /**
     * 项目空间路径
     */
    private static final String PROJECT_PATH = "main/java";

    /**
     * mybatis空间路径
     */
    private static final String MAPPER_PATH = "main/resources/mapper";

    /**
     * 设置模板变量信息
     *
     * @return 模板列表
     */
    public static VelocityContext prepareContext(GenTable genTable) {
        String moduleName = genTable.getModuleName();
        String businessName = genTable.getBusinessName();
        String packageName = genTable.getPackageName();
        String tplCategory = genTable.getTplCategory();
        String functionName = genTable.getFunctionName();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tplCategory", genTable.getTplCategory());
        velocityContext.put("tableName", genTable.getTableName());
        velocityContext.put("functionName", StringUtils.isNotEmpty(functionName) ? functionName : "【请填写功能名称】");
        velocityContext.put("ClassName", genTable.getClassName());
        velocityContext.put("className", StringUtils.uncapitalize(genTable.getClassName()));
        velocityContext.put("moduleName", genTable.getModuleName());
        velocityContext.put("BusinessName", StringUtils.capitalize(genTable.getBusinessName()));
        velocityContext.put("businessName", genTable.getBusinessName());
        velocityContext.put("basePackage", getPackagePrefix(packageName));
        velocityContext.put("packageName", packageName);
        velocityContext.put("author", genTable.getFunctionAuthor());
        velocityContext.put("datetime", DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN));
        velocityContext.put("pkColumn", genTable.getPkColumn());
        velocityContext.put("importList", getImportList(genTable.getColumns()));
        velocityContext.put("permissionPrefix", getPermissionPrefix(moduleName, businessName));
        velocityContext.put("columns", genTable.getColumns());
        velocityContext.put("table", genTable);
        velocityContext.put("sqlId", StrUtil.sub(String.valueOf(DateUtil.currentSeconds()), 0, 9));
        if (GenConstants.TPL_TREE.equals(tplCategory)) {
            setTreeVelocityContext(velocityContext, genTable);
        }
        return velocityContext;
    }

    private static void setTreeVelocityContext(VelocityContext context, GenTable genTable) {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSONUtil.parseObj(options);
        String treeCode = getTreecode(paramsObj);
        String treeParentCode = getTreeParentCode(paramsObj);
        String treeName = getTreeName(paramsObj);

        context.put("treeCode", treeCode);
        context.put("treeParentCode", treeParentCode);
        context.put("treeName", treeName);
        context.put("expandColumn", getExpandColumn(genTable));
        if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
            context.put("tree_parent_code", paramsObj.getStr(GenConstants.TREE_PARENT_CODE));
        }
        if (paramsObj.containsKey(GenConstants.TREE_NAME)) {
            context.put("tree_name", paramsObj.getStr(GenConstants.TREE_NAME));
        }
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplateList(String tplCategory) {
        List<String> templates = new ArrayList<>();
        templates.add("vm/java/domain.java.vm");
        templates.add("vm/java/dao.java.vm");
        templates.add("vm/java/service.java.vm");
        templates.add("vm/java/serviceImpl.java.vm");
        templates.add("vm/java/controller.java.vm");
        templates.add("vm/java/dao.xml.vm");
        templates.add("vm/sql/sql.vm");
        templates.add("vm/vue/api.js.vm");
        if (GenConstants.TPL_CRUD.equals(tplCategory)) {
            templates.add("vm/vue/index.vue.vm");
        } else if (GenConstants.TPL_TREE.equals(tplCategory)) {
            templates.add("vm/vue/index-tree.vue.vm");
        }
        return templates;
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, GenTable genTable) {
        // 文件名称
        String fileName = "";
        // 包路径
        String packageName = genTable.getPackageName();
        // 模块名
        String moduleName = genTable.getModuleName();
        // 大写类名
        String className = genTable.getClassName();
        // 业务名称
        String businessName = genTable.getBusinessName();

        String javaPath = PROJECT_PATH + "/" + StringUtils.replace(packageName, ".", "/");
        String mapperPath = MAPPER_PATH + "/" + moduleName;
        String vuePath = "vue";

        if (template.contains("domain.java.vm")) {
            fileName = StrUtil.format("{}/domain/{}.java", javaPath, className);
        } else if (template.contains("dao.java.vm")) {
            fileName = StrUtil.format("{}/dao/{}Dao.java", javaPath, className);
        } else if (template.contains("service.java.vm")) {
            fileName = StrUtil.format("{}/service/{}Service.java", javaPath, className);
        } else if (template.contains("serviceImpl.java.vm")) {
            fileName = StrUtil.format("{}/service/impl/{}ServiceImpl.java", javaPath, className);
        } else if (template.contains("controller.java.vm")) {
            fileName = StrUtil.format("{}/controller/{}Controller.java", javaPath, className);
        } else if (template.contains("dao.xml.vm")) {
            fileName = StrUtil.format("{}/{}Dao.xml", mapperPath, className);
        } else if (template.contains("sql.vm")) {
            fileName = businessName + "Menu.sql";
        } else if (template.contains("api.js.vm")) {
            fileName = StrUtil.format("{}/api/{}/{}.js", vuePath, moduleName, businessName);
        } else if (template.contains("index.vue.vm")) {
            fileName = StrUtil.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        } else if (template.contains("index-tree.vue.vm")) {
            fileName = StrUtil.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        }
        return fileName;
    }

    /**
     * 获取包前缀
     *
     * @param packageName 包名称
     * @return 包前缀名称
     */
    private static String getPackagePrefix(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        return StrUtil.sub(packageName, 0, lastIndex);
    }

    /**
     * 根据列类型获取导入包
     *
     * @return 返回需要导入的包列表
     */
    private static HashSet<String> getImportList(List<GenTableColumn> columns) {
        HashSet<String> importList = new HashSet<String>();
        for (GenTableColumn column : columns) {
            if (!column.isSuperColumn()) {
                if (GenConstants.TYPE_DATE.equals(column.getJavaType())) {
                    importList.add("java.util.Date");
                    importList.add("com.fasterxml.jackson.annotation.JsonFormat");
                } else if (GenConstants.TYPE_BIGDECIMAL.equals(column.getJavaType())) {
                    importList.add("java.math.BigDecimal");
                } else if (GenConstants.TYPE_LONG.equals(column.getJavaType())) {
                    importList.add("com.fasterxml.jackson.databind.annotation.JsonSerialize");
                    importList.add("com.fasterxml.jackson.databind.ser.std.ToStringSerializer");
                }
            }
        }
        return importList;
    }

    /**
     * 获取权限前缀
     *
     * @param moduleName   模块名称
     * @param businessName 业务名称
     * @return 返回权限前缀
     */
    private static String getPermissionPrefix(String moduleName, String businessName) {
        return StrUtil.format("{}:{}", moduleName, businessName);
    }

    /**
     * 获取树编码
     *
     * @return 树编码
     */
    private static String getTreecode(JSONObject paramsObj) {
        if (paramsObj.containsKey(GenConstants.TREE_CODE)) {
            return StrUtil.toCamelCase(paramsObj.getStr(GenConstants.TREE_CODE));
        }
        return "";
    }

    /**
     * 获取树父编码
     *
     * @return 树父编码
     */
    private static String getTreeParentCode(JSONObject paramsObj) {
        if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
            return StrUtil.toCamelCase(paramsObj.getStr(GenConstants.TREE_PARENT_CODE));
        }
        return "";
    }

    /**
     * 获取树名称
     *
     * @return 树名称
     */
    private static String getTreeName(JSONObject paramsObj) {
        if (paramsObj.containsKey(GenConstants.TREE_NAME)) {
            return StrUtil.toCamelCase(paramsObj.getStr(GenConstants.TREE_NAME));
        }
        return "";
    }

    /**
     * 获取需要在哪一列上面显示展开按钮
     *
     * @param genTable 业务表对象
     * @return 展开按钮列序号
     */
    private static int getExpandColumn(GenTable genTable) {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSONUtil.parseObj(options);
        String treeName = paramsObj.getStr(GenConstants.TREE_NAME);
        int num = 0;
        for (GenTableColumn column : genTable.getColumns()) {
            if (column.isList()) {
                num++;
                String columnName = column.getColumnName();
                if (columnName.equals(treeName)) {
                    break;
                }
            }
        }
        return num;
    }
}