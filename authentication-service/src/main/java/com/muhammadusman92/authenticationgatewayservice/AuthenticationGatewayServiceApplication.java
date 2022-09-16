package com.muhammadusman92.authenticationgatewayservice;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muhammadusman92.authenticationgatewayservice.exception.RestTemplateResponseErrorHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;

@SpringBootApplication
@EnableEurekaClient
public class AuthenticationGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationGatewayServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
//		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
//		return restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler())
//				.build();\
		return new RestTemplate();
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

//	@Bean
//	public ObjectMapper objectMapper() {
//		JsonFactory factory = new JsonFactory();
//		factory.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
//
//		ObjectMapper objectMapper = new ObjectMapper(factory);
////		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
////		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
////		objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
//
//		return objectMapper;
//	}



}
