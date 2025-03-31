package com.winzeler.oauth2.bff.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.TokenRelayFilterFunctions.tokenRelay;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@SpringBootApplication
public class Oauth2BffDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2BffDemoApplication.class, args);
	}

	@Bean
	public RouterFunction<ServerResponse> getRoute() {
		return route().GET("/**", http("https://httpbin.org"))
				// as in https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway-server-mvc/filters/tokenrelay.html
				.filter(tokenRelay())
				.build();
	}

}
