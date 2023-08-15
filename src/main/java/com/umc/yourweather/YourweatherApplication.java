package com.umc.yourweather;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@OpenAPIDefinition(servers = {@Server(url = "https://api.yourweather.shop/", description = "Default Server URL")})
@SpringBootApplication
public class YourweatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(YourweatherApplication.class, args);
    }

}
