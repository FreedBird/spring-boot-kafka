package com.platform.modules.common;

import cn.hutool.json.JSONUtil;
import com.platform.common.config.PlatformConfig;
import com.platform.common.core.CharsetKit;
import com.platform.common.ueditor.ActionEnter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 百度编辑器
 */
@RestController
@CrossOrigin
@RequestMapping("/common/ueditor")
@Slf4j
public class UeditorController {

    @RequestMapping(value = "/exec")
    public void exec(MultipartFile upfile, HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        request.setCharacterEncoding(CharsetKit.UTF_8);
        String action = request.getParameter("action");
        String rootPath = PlatformConfig.profile;
        try {
            if ("config".equals(action)) {
                String exec = new ActionEnter(request, rootPath).exec();
                PrintWriter writer = response.getWriter();
                writer.write(exec);
                writer.flush();
                writer.close();
            } else if ("uploadimage".equals(action)) {
                PrintWriter writer = response.getWriter();
                Map<String, Object> map = new HashMap<>();
                try {
                    String name = upfile.getOriginalFilename();
                    // 执行上传 TODO
//                    String path = FastDFSUtils.uploadImageAndCrtThumbImage(upfile);
//                    String url = FastDFSUtils.getWebServerUrl() + path;
                    String url = "https://www.baidu.com";
                    map.put("name", name);
                    map.put("url", url);
                    map.put("state", "SUCCESS");
                    map.put("size", "" + upfile.getSize());
                    map.put("title", name);
                    map.put("original", name);
                } catch (Exception e) {
                    log.error("上传失败", e);
                    map.put("state", "ERROR");
                }
                writer.write(JSONUtil.toJsonStr(map));
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            log.error("exec出错", e);
        }
    }

}
