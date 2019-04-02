package com.oasys.oaeureka7001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class OaEureka7001Application {

    public static void main(String[] args) {
        SpringApplication.run(OaEureka7001Application.class, args);
    }

}
