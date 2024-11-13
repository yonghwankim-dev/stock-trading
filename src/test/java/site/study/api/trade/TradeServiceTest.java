package site.study.api.trade;

import java.util.concurrent.CompletableFuture;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import site.stocktrading.StockTradingApplication;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;
import site.stocktrading.api.trade.service.TradeService;

@SpringBootTest(classes = StockTradingApplication.class)
class TradeServiceTest {

	@Autowired
	private TradeService service;

	@DisplayName("종목과 수량이 주어지고 수량이 0인 경우에 null을 반환한다")
	@Test
	void givenStockAndQuantity_whenBuyStockWithNotEnoughQuantity_thenReturnNull() {
		// given
		Stock samsung = new Stock("samsung", 50000);
		int quantity = 0;
		// when
		CompletableFuture<Order> future = service.buyStock(samsung, quantity)
			.handle((order, throwable) -> {
				if (throwable != null) {
					return null;
				}
				return order;
			});
		// then
		Order actual = future.join();
		Assertions.assertThat(actual).isNull();
	}
}
