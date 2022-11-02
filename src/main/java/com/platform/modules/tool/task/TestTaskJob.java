package com.platform.modules.tool.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestTaskJob {

    public void doTask1() {
        log.info("无参运行....");
    }

    public void doTask2(String str) {
        log.info("单参运行....{}", str);
    }

    public void doTask3(String str1, String str2) {
        log.info("多参运行....{},{}", str1, str2);
    }
}
