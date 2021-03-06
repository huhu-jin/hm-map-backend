package com.jin.humap;

import com.maihaoche.starter.mq.annotation.EnableMQConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMQConfiguration
@MapperScan(value = "com.jin.humap.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
