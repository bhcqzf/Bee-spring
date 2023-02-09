package com.fastyw.bee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//注册过滤器注解
@ServletComponentScan
public class BeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeeApplication.class, args);
	}

}
