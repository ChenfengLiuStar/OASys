package com.oasys.oaeureka7003;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class OaEureka7003Application {

    public static void main(String[] args) {
        SpringApplication.run(OaEureka7003Application.class, args);
    }

}
