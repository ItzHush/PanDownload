package com.pandownload;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pandownload.mapper")
public class PandownloadApplication {
    public static void main(String[] args) {
        SpringApplication.run(PandownloadApplication.class, args);
        System.out.println(">>>>>>>>>>>RUNNING>>>>>>>>>>>");
    }

}
