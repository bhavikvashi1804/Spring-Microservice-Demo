package com.bhavik.microservice.currencyconversion;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	CurrencyExchangeProxy proxy;
	
	
	@GetMapping("/currency-conversion-rest/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity ) {
		
		
		String URL = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(URL, CurrencyConversion.class, uriVariables);
		CurrencyConversion currencyConversion = responseEntity.getBody();
		currencyConversion.setQuantity(quantity);
		currencyConversion.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversionMultiple()));
		
		return currencyConversion;
	}

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calCurrencyConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity ) {
		
		System.out.println("Yes");
		CurrencyConversion currencyConversion = proxy.getCurrencyExchangeRate(from, to);
		currencyConversion.setQuantity(quantity);
		currencyConversion.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversionMultiple()));
		return currencyConversion;
	}
	
	@GetMapping("/data")
	public String data() {
		System.out.println("yes data");
		return "data";
	}

}
