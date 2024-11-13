package site.stocktrading.api.stock.service;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.stock.exception.StockException;
import site.stocktrading.api.stock.repository.StockRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {
	private final StockRepository repository;

	public void saveStock(Stock stock) {
		Stock save = repository.save(stock);
		log.info("save the Stock, stock={}", save);
	}

	public List<Stock> findAll() {
		return repository.findAll();
	}

	public Stock find(String name) {
		return repository.find(name).orElseThrow(notFoundStockFunction(name));
	}

	private Supplier<StockException> notFoundStockFunction(String name) {
		return () -> new StockException("not found stock, name=" + name);
	}

	public void deleteStock(String name) {
		Stock deleted = repository.deleteStock(name);
		log.info("delete the Stock, stock={}", deleted);
	}
}
