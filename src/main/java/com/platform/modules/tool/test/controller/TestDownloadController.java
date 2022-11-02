package com.platform.modules.tool.test.controller;

import com.alibaba.excel.ExcelWriter;
import com.platform.common.web.controller.BaseController;
import com.platform.modules.tool.test.service.TestDownloadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test/download")
public class TestDownloadController extends BaseController {

    @Resource
    private TestDownloadService downloadService;

    @GetMapping("/test1")
    public void test1() {
        // 设置请求excel请求
        ExcelWriter excelWriter = startExcel("template/demo2.xlsx", "测试");
        // 执行导出数据
        downloadService.test1(excelWriter);
        // 别忘记关闭流
        excelWriter.finish();
    }

}
