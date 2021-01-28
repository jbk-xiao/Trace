package com.JingDong.JingDong;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan(basePackages = {"com.JingDong.JingDong.mapper"})
@SpringBootApplication
public class JingDongSpringboot192Application {

	public static void main(String[] args) {
		SpringApplication.run(com.JingDong.JingDong.JingDongSpringboot192Application.class, args);
	}

}
