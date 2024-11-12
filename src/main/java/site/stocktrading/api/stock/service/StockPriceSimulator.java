package site.stocktrading.api.stock.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.stocktrading.api.stock.domain.Stock;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockPriceSimulator {

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private final StockService service;
	private final RangeRandomPercentageGenerator generator;

	public void startSimulation() {
		scheduler.scheduleAtFixedRate(this::updatePrices, 0, 1, TimeUnit.SECONDS);
	}

	private void updatePrices() {
		service.findAll().stream()
			.map(stock -> stock.newStock(generator))
			.map(service::saveStock)
			.forEach(this::printStock);
	}

	private void printStock(Stock stock) {
		System.out.printf("Updated price to %s%n", stock);
	}

	public void stopSimulation() {
		scheduler.shutdown();
		try {
			if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
				scheduler.shutdownNow();
			}
		} catch (InterruptedException e) {
			log.warn("Interrupted!", e);
			scheduler.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}
}
