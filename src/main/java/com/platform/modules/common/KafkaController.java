package com.platform.modules.common;/**
 * Created by Enzo Cotter on 2021/3/18.
 */

import com.platform.common.kafka.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 何焱
 * @date 2021/3/18 15:20
 */
@RestController
@CrossOrigin
@RequestMapping("/common/kafka")
@Slf4j
public class KafkaController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping("/send")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public void list() {
        kafkaProducer.send("测试消息！！！");
    }

    @GetMapping("/test")
    public String name (){
        return "121212";
    }
}
