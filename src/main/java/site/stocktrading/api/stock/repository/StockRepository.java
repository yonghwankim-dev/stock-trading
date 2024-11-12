package site.stocktrading.api.stock.repository;

import site.stocktrading.api.stock.domain.Stock;

public interface StockRepository {

	Stock save(Stock stock);
}
