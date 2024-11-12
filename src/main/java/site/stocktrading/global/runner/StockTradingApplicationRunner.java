package site.stocktrading.global.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import site.stocktrading.api.stock.service.StockPriceSimulator;

@Component
@RequiredArgsConstructor
public class StockTradingApplicationRunner implements ApplicationRunner {

	private final StockPriceSimulator simulator;

	@Override
	public void run(ApplicationArguments args) {
		simulator.startSimulation();
	}
}
