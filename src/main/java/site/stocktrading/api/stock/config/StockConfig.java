package site.stocktrading.api.stock.config;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import site.stocktrading.api.stock.service.RangeRandomPercentageGenerator;

@Configuration
public class StockConfig {

	@Bean
	public RangeRandomPercentageGenerator rangeRandomPercentageGenerator(){
		ThreadLocalRandom random = ThreadLocalRandom.current();
		double range = 0.01;
		return new RangeRandomPercentageGenerator(random, range);
	}
}
