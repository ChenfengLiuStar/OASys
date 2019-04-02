package com.oasys.oazuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class OaZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(OaZuulApplication.class, args);
    }

}
