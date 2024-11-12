package site.stocktrading.api.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.stock.repository.StockRepository;

@Service
@RequiredArgsConstructor
public class StockService {
	private final StockRepository repository;

	public Stock saveStock(Stock stock){
		return repository.save(stock);
	}

	public List<Stock> findAll() {
		return repository.findAll();
	}
}
