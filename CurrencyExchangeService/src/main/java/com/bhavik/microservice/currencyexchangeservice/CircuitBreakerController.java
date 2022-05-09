package com.bhavik.microservice.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {
	
	
	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

	
	@GetMapping("sample-data")
	//@Retry(name = "sample-api", fallbackMethod = "fallBackSendData")
	@CircuitBreaker(name="default", fallbackMethod = "fallBackSendData")
	public String sendData() {
		logger.info("Sample API call");
		ResponseEntity<String> entity =
				new RestTemplate().getForEntity("http://localhost:8080/dummy", String.class);
		return entity.getBody();
	}
	
	public String fallBackSendData(Exception ex){
		return "API Dummy is not available";
	}
}
