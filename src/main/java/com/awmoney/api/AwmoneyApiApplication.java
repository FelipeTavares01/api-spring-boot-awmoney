package com.awmoney.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.awmoney.api.configuration.property.AwMoneyApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(AwMoneyApiProperty.class)
public class AwmoneyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwmoneyApiApplication.class, args);
	}

}
