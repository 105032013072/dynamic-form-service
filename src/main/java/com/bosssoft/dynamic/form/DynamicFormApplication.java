package com.bosssoft.dynamic.form;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"com.bosssoft"})
@EnableTransactionManagement
public class DynamicFormApplication {
    public static void main(String[] args) {
        SpringApplication.run(DynamicFormApplication.class, args);
    }
}
