package com.muhammadusman92.gatewayservice;

import com.muhammadusman92.gatewayservice.filters.AuthenticationPrefilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableEurekaClient
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}
//	@Autowired
//	ApplicationProperties applicationProperties;
//
//	app;
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	@Bean
	public RouteLocator routes(
			RouteLocatorBuilder builder,
			AuthenticationPrefilter authFilter) {
		return builder.routes()
				.route("auth-service-route", r -> r.path("/authentication-service/**")
						.filters(f ->
								f.rewritePath("/authentication-service(?<segment>/?.*)", "$\\{segment}")
										.circuitBreaker(config -> config
												.setName("CircuitBreaker")
												.setFallbackUri("/authServiceFallBack"))
										.filter(authFilter.apply(
												new AuthenticationPrefilter.Config()))
						)
						.uri("lb://AUTHENTICATION-SERVICE"))
				.route("user-service-route", r -> r.path("/user-service/**")
						.filters(f ->
								f.rewritePath("/user-service(?<segment>/?.*)", "$\\{segment}")
										.filter(authFilter.apply(
												new AuthenticationPrefilter.Config()))
										.circuitBreaker(config -> config
												.setName("CircuitBreaker")
												.setFallbackUri("/userServiceFallBack"))
						)
						.uri("lb://USER-SERVICE"))
				.route("store-service-route", r -> r.path("/store-service/**")
						.filters(f ->
								f.rewritePath("/store-service(?<segment>/?.*)", "$\\{segment}")
										.filter(authFilter.apply(
												new AuthenticationPrefilter.Config()))
										.circuitBreaker(config -> config
												.setName("CircuitBreaker")
												.setFallbackUri("/storeServiceFallBack"))
						)
						.uri("lb://STORE-SERVICE"))
				.route("account-service-route", r -> r.path("/account-service/**")
						.filters(f ->
								f.rewritePath("/account-service(?<segment>/?.*)", "$\\{segment}")
										.filter(authFilter.apply(
												new AuthenticationPrefilter.Config()))
										.circuitBreaker(config -> config
												.setName("CircuitBreaker")
												.setFallbackUri("/accountServiceFallBack"))
						)
						.uri("lb://ACCOUNT-SERVICE"))
				.build();
	}
	@Value("${spring.gateway.excludedURLsNew}")
	private String urlsStrings;

	@Bean
	@Qualifier("excludedUrls")
	public List<String> excludedUrls() {
		return Arrays.stream(urlsStrings.split(",")).collect(Collectors.toList());
	}

}
