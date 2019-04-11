package com.identity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.web3j.protocol.Web3j;

@SpringBootApplication
@ComponentScan(basePackages = {"com.identity.*"})
public class IdentityApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdentityApplication.class, args);
	}

}

