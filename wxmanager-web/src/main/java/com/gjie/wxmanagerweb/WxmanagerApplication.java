package com.gjie.wxmanagerweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.gjie")
public class WxmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WxmanagerApplication.class, args);
	}

}
