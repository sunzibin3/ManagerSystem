package com.sun.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@ServletComponentScan(basePackages = "com.sun.admin.servelet")//配置原生Servlet扫描路径
@SpringBootApplication
public class BootO5AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootO5AdminApplication.class, args);
    }

}
