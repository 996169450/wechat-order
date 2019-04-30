package com.hnu;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = "com.hnu.wechatorder.dao")
public class WechatOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WechatOrderApplication.class, args);
	}

//	@Bean
//	PageHelper pageHelper(){
//		//分页插件
//		PageHelper pageHelper = new PageHelper();
//		Properties properties = new Properties();
//		properties.setProperty("reasonable", "true");      //超出页码范围，定位到第一页或最后一页
//		properties.setProperty("supportMethodsArguments", "true");
//		properties.setProperty("returnPageInfo", "check");
//		properties.setProperty("params", "count=countSql");
//		pageHelper.setProperties(properties);
//
//		//添加插件
//		new SqlSessionFactoryBean().setPlugins(new Interceptor[]{pageHelper});
//		return pageHelper;
//	}
}
