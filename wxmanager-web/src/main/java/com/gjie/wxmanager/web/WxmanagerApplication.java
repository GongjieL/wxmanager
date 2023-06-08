package com.gjie.wxmanager.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = {"com.gjie.wxmanager"})
@PropertySource({"classpath:application.properties",
        "classpath:application-api.properties",
        "classpath:application-dao.properties"})
@MapperScan({"com.gjie.wxmanager.dao"})
public class WxmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxmanagerApplication.class, args);
    }

}
