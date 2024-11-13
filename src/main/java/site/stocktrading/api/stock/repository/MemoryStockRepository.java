package site.stocktrading.api.stock.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import site.stocktrading.api.stock.domain.Stock;

@Repository
public class MemoryStockRepository implements StockRepository {

	private final Map<String, Stock> store = new ConcurrentHashMap<>();

	@Override
	public Stock save(Stock stock) {
		return stock.saveTo(store);
	}

	@Override
	public List<Stock> findAll() {
		return store.values().stream()
			.toList();
	}

	@Override
	public Optional<Stock> find(String name) {
		return Optional.ofNullable(store.get(name));
	}

	@Override
	public void deleteStock(String name) {
		store.remove(name);
	}
}
