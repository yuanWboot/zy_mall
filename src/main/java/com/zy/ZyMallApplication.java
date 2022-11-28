package com.zy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.zy.mall.model.dao")
public class ZyMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZyMallApplication.class, args);
    }

}
