package com.bhavik.microservice.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {
	
	
	@Bean
	public RouteLocator getwayRouter(RouteLocatorBuilder builder) {
		
		return builder.routes()
				.route(p -> p.path("/currency-exchange/**").uri("lb://currency-exchange"))
				.route(p -> p.path("/currency-conversion/**").uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-rest/**").uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-new/**")
						.filters(f -> f.rewritePath(
								"/currency-conversion-new/(?<segment>.*)",
								"/currency-conversion/${segment}" ))
						.uri("lb://currency-conversion/"))
				.build();
	}

}


// 192.168.0.106:currency-conversion:8100