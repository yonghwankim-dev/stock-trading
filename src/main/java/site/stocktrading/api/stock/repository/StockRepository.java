package site.stocktrading.api.stock.repository;

import java.util.List;
import java.util.Optional;

import site.stocktrading.api.stock.domain.Stock;

public interface StockRepository {

	Stock save(Stock stock);

	List<Stock> findAll();

	Optional<Stock> find(String name);

	Stock deleteStock(String name);

}
