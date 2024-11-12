package site.stocktrading.api.stock.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;

import site.stocktrading.api.stock.domain.Stock;

@Repository
public class MemoryStockRepository implements StockRepository{

	private final List<Stock> store = new CopyOnWriteArrayList<>();

	@Override
	public Stock save(Stock stock) {
		store.add(stock);
		return stock;
	}
}
