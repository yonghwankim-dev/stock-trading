package site.stocktrading.api.stock.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.stereotype.Component;

import site.stocktrading.api.stock.domain.Stock;

@Component
public class StockParser {

	public List<Stock> parseStocks(InputStream inputStream) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			return br.lines()
				.skip(1)
				.map(line -> line.split(","))
				.map(this::parseStock)
				.toList();
		} catch (IOException e) {
			throw new IllegalStateException("fail parsing stocks", e);
		}
	}

	private Stock parseStock(String[] columns) {
		final int nameColumn = 2;
		return Stock.zero(columns[nameColumn]);
	}
}
