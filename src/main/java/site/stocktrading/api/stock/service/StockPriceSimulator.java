package site.stocktrading.api.stock.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockPriceSimulator {

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private final StockService service;
	private final RangeRandomPercentageGenerator generator;

	public void startSimulation() {
		final int initialDelay = 0;
		final int period = 10;
		scheduler.scheduleAtFixedRate(this::updatePrices, initialDelay, period, TimeUnit.SECONDS);
	}

	private void updatePrices() {
		service.findAll().stream()
			.map(stock -> stock.newStock(generator))
			.forEach(service::saveStock);
		log.info("finish updatePrices");
	}

	@PreDestroy
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
		log.info("shutdown scheduler {}", scheduler);
	}
}
