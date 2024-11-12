package site.stocktrading.global.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import site.stocktrading.api.stock.service.RandomPriceGenerator;
import site.stocktrading.api.stock.service.StockFileReader;
import site.stocktrading.api.stock.service.StockParser;
import site.stocktrading.api.stock.service.StockService;

@Profile(value = {"!test"})
@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
	private boolean alreadySetup = false;
	private final StockFileReader reader;
	private final StockParser parser;
	private final StockService service;
	private final RandomPriceGenerator generator;

	@Override
	public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}
		parser.parseStocks(reader.readStockFile("stocks.csv")).stream()
			.map(stock -> stock.newStock(generator))
			.forEach(service::saveStock);
		alreadySetup = true;
	}
}
