package site.stocktrading.api.stock.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class StockFileReader {

	public InputStream readStockFile(String path) {
		InputStream inputStream;
		try {
			inputStream = new ClassPathResource(path).getInputStream();
		} catch (IOException e) {
			throw new IllegalArgumentException("not found resource", e);
		}
		return inputStream;
	}
}
