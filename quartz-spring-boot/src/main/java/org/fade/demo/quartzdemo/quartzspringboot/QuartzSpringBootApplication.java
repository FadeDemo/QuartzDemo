package org.fade.demo.quartzdemo.quartzspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fade
 * @date 2021/11/24
 */
@SpringBootApplication
public class QuartzSpringBootApplication {

    public static void main(String[] args) {
        // todo 理解quartz的持久化，
        //  为什么trigger的信息只在misfired时持久化到数据库中
        SpringApplication.run(QuartzSpringBootApplication.class, args);
    }

}
