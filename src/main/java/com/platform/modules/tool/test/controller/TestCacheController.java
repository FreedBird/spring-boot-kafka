package com.platform.modules.tool.test.controller;

import com.platform.common.web.controller.BaseController;
import com.platform.common.web.domain.AjaxResult;
import com.platform.modules.tool.test.service.TestCacheService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test/cache")
public class TestCacheController extends BaseController {

    @Resource
    private TestCacheService testCacheService;

    @GetMapping("/test1/{key}")
    public AjaxResult test1(@PathVariable Long key) {
        return AjaxResult.success(testCacheService.test1(key));
    }

    @GetMapping("/test2/{key}")
    public AjaxResult test2(@PathVariable Long key) {
        testCacheService.test2(key);
        return AjaxResult.success();
    }

}
