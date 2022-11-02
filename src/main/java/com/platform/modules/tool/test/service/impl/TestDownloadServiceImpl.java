package com.platform.modules.tool.test.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.deepoove.poi.XWPFTemplate;
import com.platform.modules.tool.test.service.TestDownloadService;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestDownloadServiceImpl implements TestDownloadService {

    @Override
    public void test1(ExcelWriter excelWriter) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("sex", "男");
        map.put("age", 5.2);
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);
        list.add(map);
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        excelWriter.fill(map, writeSheet);
        excelWriter.fill(list, writeSheet);
        excelWriter.finish();
    }

    private static void demo1() throws IOException {
        XWPFTemplate template = XWPFTemplate.compile("E:\\test1.docx").render(
                new HashMap<String, Object>() {{
                    put("title", "Hi, poi-tl Word模板引擎");
                }});
        template.writeAndClose(new FileOutputStream("E:\\output.docx"));
    }

}
