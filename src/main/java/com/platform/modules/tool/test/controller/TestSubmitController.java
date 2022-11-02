package com.platform.modules.tool.test.controller;

import com.platform.common.aspectj.lang.annotation.SubmitRepeat;
import com.platform.common.web.domain.AjaxResult;
import com.platform.modules.sys.vo.LoginBody;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test/submit")
public class TestSubmitController {

    @SubmitRepeat
    @GetMapping("/test1")
    public AjaxResult test1() {
        return AjaxResult.success();
    }

    @SubmitRepeat
    @GetMapping("/test2")
    public AjaxResult test2() {
        return AjaxResult.success();
    }

    @SubmitRepeat
    @GetMapping("/test3/{id}")
    public AjaxResult test3(@PathVariable Long id) {
        return AjaxResult.success(id);
    }

    @SubmitRepeat
    @PostMapping("/test4")
    public AjaxResult test4(String str1, String str2) {
        return AjaxResult.success(str1 + str2);
    }

    @SubmitRepeat
    @PostMapping("/test5")
    public AjaxResult test5(@RequestBody LoginBody loginBody) {
        return AjaxResult.success(loginBody.getUsername() + loginBody.getPassword());
    }

}
