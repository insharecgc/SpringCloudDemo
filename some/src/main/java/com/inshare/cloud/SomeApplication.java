package com.inshare.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SomeApplication {
	
	   public static void main(String[] args) {
	        SpringApplication.run(SomeApplication.class, args);
	    }
}
