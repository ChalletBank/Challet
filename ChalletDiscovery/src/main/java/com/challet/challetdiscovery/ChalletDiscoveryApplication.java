package com.challet.challetdiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ChalletDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChalletDiscoveryApplication.class, args);
    }

}
