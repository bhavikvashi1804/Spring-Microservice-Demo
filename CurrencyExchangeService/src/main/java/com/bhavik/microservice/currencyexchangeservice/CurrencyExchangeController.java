package com.bhavik.microservice.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	
	@Autowired
	Environment environment;
	
	@Autowired
	CurrencyExchangeRepository currencyExchangeRepository;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange getCurrencyExchangeRate(@PathVariable String from, @PathVariable String to) {
		//CurrencyExchange currencyExchange = new CurrencyExchange(1001L, "USD", "INR", BigDecimal.valueOf(75.45));
		
		CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
		if(currencyExchange == null) {
			throw new RuntimeException("Data not found");
		}
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		return currencyExchange;
	}

}