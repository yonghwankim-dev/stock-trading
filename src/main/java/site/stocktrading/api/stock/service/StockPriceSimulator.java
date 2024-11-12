package site.stocktrading.api.stock.service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import site.stocktrading.api.stock.domain.Stock;

@Component
@RequiredArgsConstructor
public class StockPriceSimulator {

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private final Random random = new Random();
	private final StockService service;
	private final RangeRandomPercentageGenerator generator;

	public void startSimulation(){
		scheduler.scheduleAtFixedRate(this::updatePrices, 0, 1, TimeUnit.SECONDS);
	}

	private void updatePrices() {
		service.findAll().stream()
			.map(stock->stock.newStock(generator))
			.map(service::saveStock)
			.forEach(this::printStock);
	}

	private void printStock(Stock stock){
		System.out.printf("Updated price to %s%n", stock);
	}

	// 시뮬레이션 종료
	public void stopSimulation(){
		scheduler.shutdown();
		try{
			if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
				scheduler.shutdownNow();
			}
		} catch (InterruptedException e) {
			scheduler.shutdownNow();
		}
	}
}
