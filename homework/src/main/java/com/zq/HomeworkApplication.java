package com.zq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhaoqi
 * @version 1.8
 */
@SpringBootApplication
@MapperScan("com.zq.dao")
public class HomeworkApplication {
    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(HomeworkApplication.class,args);
    }
}
