package com.gjie.wxmanager.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan("com.gjie.wxmanager")
@PropertySource({"classpath:application.properties","classpath:application-api.properties"})
public class WxmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WxmanagerApplication.class, args);
	}

}
