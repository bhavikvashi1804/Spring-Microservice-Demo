package com.bhavik.microservice.currencyconversion;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="currency-exchange")
public interface CurrencyExchangeProxy {
	
	//copy the method signature from other microservice
	//change the return type
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion getCurrencyExchangeRate(@PathVariable String from, @PathVariable String to);

}
