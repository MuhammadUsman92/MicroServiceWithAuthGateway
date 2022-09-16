package com.muhammadusman92.gatewayservice.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Predicate;


@Component
@Slf4j
public class AuthenticationPrefilter extends AbstractGatewayFilterFactory<AuthenticationPrefilter.Config> {

    @Autowired
    @Qualifier("excludedUrls")
    List<String> excludedUrls;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            log.info("**************************************************************************");
            log.info("URL is - " + request.getURI().getPath());
            String bearerToken = request.getHeaders().getFirst("Authorization");
            log.info("Bearer Token: "+ bearerToken);

            if(isSecured.test(request)) {
                try {
                    HttpHeaders headers = request.getHeaders();
                    HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
                    ResponseEntity<String> authResponse = restTemplate.exchange("http://AUTHENTICATION-SERVICE/api/v1/auth/", HttpMethod.GET, httpEntity, String.class);
                }catch (HttpStatusCodeException e){
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }
            }
            return chain.filter(exchange);
        };
    }

    public Predicate<ServerHttpRequest> isSecured
            = request -> excludedUrls.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
    @NoArgsConstructor
    public static class Config {


    }
}
