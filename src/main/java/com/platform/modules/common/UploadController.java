package com.platform.modules.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.platform.common.aspectj.lang.annotation.SubmitRepeat;
import com.platform.common.constant.Constants;
import com.platform.common.core.CharsetKit;
import com.platform.common.exception.BaseException;
import com.platform.common.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用请求处理
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class UploadController {

    /**
     * 通用上传请求
     */
    @RequiresPermissions(Constants.PERM_SYS)
    @PostMapping("/upload")
    @SubmitRepeat
    public AjaxResult uploadFile(MultipartFile file) {
        if (file == null) {
            throw new BaseException("上传文件不能为空");
        }
        // 调用上传，返回上传目录
//        String path = FastDFSUtils.uploadFile(file);
        // 获取域名（取配置文件）
//        String serverUrl = FastDFSUtils.getWebServerUrl();
        // 调用上传，返回上传目录
        String path = "group1/M00/00/00/CmQCymAdGqCAbQLhAADFNKFGhgo381.png";
        // 获取域名（取配置文件）
        String serverUrl = "https://upload.q3z3.com/";
        // 文件名称
        String fileName = file.getOriginalFilename();
        return AjaxResult.success().put("fileName", fileName)
                .put("serverUrl", serverUrl)
                .put("path", path)
                .put("fullPath", serverUrl + path);
    }

    /**
     * 本地资源通用下载
     */
    @RequiresPermissions(Constants.PERM_SYS)
    @GetMapping("/download/{type}")
    public void resourceDownload(@PathVariable String type, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置字符集
        request.setCharacterEncoding(CharsetKit.UTF_8);
        // 设置返回内容格式
        response.setContentType("application/octet-stream");
        String path;
        String fileName;
        switch (type) {
            case "1":
                path = "xxxx.pdf";
                fileName = "xxxxx.pdf";
                break;
            default:
                throw new BaseException("模板路径不正确");
        }
        fileName = new String(fileName.getBytes(CharsetKit.UTF_8), CharsetKit.ISO_8859_1);
        // 设置下载弹窗的文件名和格式（文件名要包括名字和文件格式）
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        IoUtil.write(response.getOutputStream(), true, FileUtil.readBytes("template/" + path));
    }

}
