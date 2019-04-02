package com.oasys.oalcfdemoweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.oasys.oalcfdemoweb.controller","com.oasys.oaapi.service.hystrix"})
@EnableFeignClients(basePackages = {"com.oasys.oaapi.service"})
public class OaLcfDemoWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(OaLcfDemoWebApplication.class, args);
    }

}
