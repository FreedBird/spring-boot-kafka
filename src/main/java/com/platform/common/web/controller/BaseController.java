package com.platform.common.web.controller;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.common.aspectj.lang.enums.BusinessType;
import com.platform.common.core.CharsetKit;
import com.platform.common.core.EnumUtils;
import com.platform.common.enums.GenderTypeEnum;
import com.platform.common.enums.ResultCodeEnum;
import com.platform.common.enums.YesOrNoEnum;
import com.platform.common.shiro.ShiroUtils;
import com.platform.common.utils.ServletUtils;
import com.platform.common.web.domain.AjaxResult;
import com.platform.common.web.page.PageDomain;
import com.platform.common.web.page.TableDataInfo;
import com.platform.common.web.page.TableSupport;
import com.platform.modules.sys.domain.SysRole;
import com.platform.modules.sys.domain.SysUser;
import com.platform.modules.sys.enums.NoticeTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * web层通用数据处理
 */
@Slf4j
public class BaseController {

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(parseDate(text));
            }
        });
        // YesOrNoEnum 类型转换
        binder.registerCustomEditor(YesOrNoEnum.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(EnumUtils.toEnum(YesOrNoEnum.class, text));
            }
        });
        // BusinessType 类型转换
        binder.registerCustomEditor(BusinessType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(EnumUtils.toEnum(BusinessType.class, text));
            }
        });
        // GenderTypeEnum 类型转换
        binder.registerCustomEditor(GenderTypeEnum.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(EnumUtils.toEnum(GenderTypeEnum.class, text));
            }
        });
        // NoticeTypeEnum 类型转换
        binder.registerCustomEditor(NoticeTypeEnum.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(EnumUtils.toEnum(NoticeTypeEnum.class, text));
            }
        });
    }

    /**
     * 前台日期格式
     */
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};


    /**
     * 日期型字符串转化为日期 格式
     */
    private static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return DateUtils.parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageDomain pageDomain = startPageDomain();
        startPage(escapeOrderBySql(pageDomain.getOrderBy()));
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage(String orderBy) {
        PageDomain pageDomain = startPageDomain();
        PageHelper.startPage(pageDomain.getPageNum(), pageDomain.getPageSize(), orderBy);
    }

    /**
     * 设置请求excel请求
     */
    protected ExcelWriter startExcel(String template, String fileName) {
        ExcelWriter excelWriter = null;
        try {
            HttpServletResponse response = ServletUtils.getResponse();
            String templateFileName = ResourceUtils.getURL("classpath:" + template).getPath();
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding(CharsetKit.UTF_8);
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, CharsetKit.UTF_8) + ".xlsx");
            excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).build();
        } catch (Exception e) {
            log.error("导出异常", e);
        }
        return excelWriter;
    }

    /**
     * 设置请求分页数据
     */
    protected PageDomain startPageDomain() {
        return TableSupport.buildPageRequest();
    }

    /**
     * 检查字符，防止注入绕过
     */
    private static String escapeOrderBySql(String value) {
        // 仅支持字母、数字、下划线、空格、逗号（支持多个字段排序）
        String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,]+";
        if (StringUtils.isNotEmpty(value) && !value.matches(SQL_PATTERN)) {
            return StringUtils.EMPTY;
        }
        return value;
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        return formatData(list, new PageInfo(list).getTotal());
    }

    protected TableDataInfo getDataTable(List<?> list, PageDomain pageDomain) {
        return getDataTable(CollUtil.sub(list, pageDomain.getPageStart(), pageDomain.getPageEnd()));
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(PageInfo<?> list) {
        return formatData(list.getList(), list.getTotal());
    }

    /**
     * 格式化分页
     */
    private TableDataInfo formatData(List<?> list, Long total) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(ResultCodeEnum.SUCCESS.getCode());
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(total);
        return rspData;
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.fail();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    protected SysUser getSysUser() {
        return ShiroUtils.getSysUser();
    }

    /**
     * 获取角色信息
     *
     * @return
     */
    protected SysRole getSysRole() {
        return ShiroUtils.getSysRole();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    protected Long getSysUserId() {
        return getSysUser().getUserId();
    }

}
