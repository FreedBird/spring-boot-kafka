package com.platform.modules.tool.test.controller;

import com.platform.common.web.domain.AjaxResult;
import com.platform.modules.tool.test.service.TestSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/source")
public class TestSourceController {

    @Autowired
    private TestSourceService testSourceService;

    @GetMapping("/test1")
    public AjaxResult test1() {
        return AjaxResult.success(testSourceService.test1());
    }

    @GetMapping("/test2")
    public AjaxResult test2() {
        testSourceService.test2();
        return AjaxResult.success();
    }

    @GetMapping("/test3")
    public AjaxResult test3() {
        return AjaxResult.success(testSourceService.test3());
    }

}
