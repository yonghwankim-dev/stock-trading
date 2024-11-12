package site.stocktrading.api.trade.service;

import java.util.concurrent.CompletableFuture;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import site.stocktrading.api.stock.domain.Stock;

@SpringBootTest
class TradeServiceTest {

	@Autowired
	private TradeService service;

	@DisplayName("종목이 주어지고 매수를 하는 경우에 비동기를 반환한다")
	@Test
	void givenStock_whenBuyStock_thenReturnFuture() {
		// given
		Stock samsung = new Stock("samsung", 50000);
		int quantity = 10;
		// when
		CompletableFuture<String> future = service.buyStock(samsung, quantity);
		// then
		String actual = future.join();
		String expected = "Successfully bought " + quantity + " shares of " + samsung;
		Assertions.assertThat(actual).hasToString(expected);
	}
}
