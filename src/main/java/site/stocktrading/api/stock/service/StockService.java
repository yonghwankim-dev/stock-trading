package site.stocktrading.api.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.stock.exception.StockException;
import site.stocktrading.api.stock.repository.StockRepository;

@Service
@RequiredArgsConstructor
public class StockService {
	private final StockRepository repository;

	public void saveStock(Stock stock) {
		repository.save(stock);
	}

	public List<Stock> findAll() {
		return repository.findAll();
	}

	public Stock find(String name) {
		return repository.find(name).orElseThrow(() -> new StockException("not found stock, name=" + name));
	}

	public void deleteStock(String name) {
		repository.deleteStock(name);
	}
}
