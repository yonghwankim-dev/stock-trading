package site.stocktrading.api.stock.service;

import java.io.InputStream;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import site.stocktrading.api.stock.domain.Stock;

class StockParserTest {

	private StockParser parser;

	@BeforeEach
	void setUp() {
		parser = new StockParser();
	}

	@DisplayName("csv 파일을 파싱하여 종목 리스트로 반환한다")
	@Test
	void givenCsvFile_whenParseStocks_thenReturnListOfStock() {
		// given
		StockFileReader reader = new StockFileReader();
		InputStream inputStream = reader.readStockFile("stocks.csv");
		// when
		List<Stock> stocks = parser.parseStocks(inputStream);
		// then
		Assertions.assertThat(stocks).hasSize(2803);
	}
}
