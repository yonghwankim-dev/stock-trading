package site.stocktrading.api.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import site.stocktrading.api.stock.service.RangeRandomPercentageGenerator;

@Configuration
public class StockConfig {

	@Bean
	public RangeRandomPercentageGenerator rangeRandomPercentageGenerator(){
		return new RangeRandomPercentageGenerator(0.01);
	}
}
