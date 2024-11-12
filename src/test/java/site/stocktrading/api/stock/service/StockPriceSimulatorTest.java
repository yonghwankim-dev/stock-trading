package site.stocktrading.api.stock.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.stock.repository.StockRepository;

@SpringBootTest
class StockPriceSimulatorTest {

	@Autowired
	private StockRepository repository;

	@Autowired
	private StockPriceSimulator simulator;

	@BeforeEach
	void setup(){
		repository.save(new Stock("samsung", 50000));
		repository.save(new Stock("kakao", 40000));
		repository.save(new Stock("sk hynix", 30000));
	}

	@DisplayName("종목 가격을 10초간 시뮬레이션한다")
	@Test
	void givenStocks_whenSimulation_thenChangeStockPrice() throws InterruptedException {
	    // given

	    // when
		simulator.startSimulation();
	    // then
		Thread.sleep(10000);
		simulator.stopSimulation();
		List<Stock> stocks = repository.findAll();
		Assertions.assertThat(stocks).hasSize(3);
	}
}
