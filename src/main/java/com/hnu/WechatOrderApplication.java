package com.hnu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.hnu.wechatorder.dao")
public class WechatOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WechatOrderApplication.class, args);
	}
}
