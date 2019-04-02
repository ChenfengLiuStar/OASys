package com.oasys.oalcfdemoservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Cacheable
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.oasys.oalcfdemoservice.dao")
public class OaLcfDemoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OaLcfDemoServiceApplication.class, args);
    }

}
