package site.study.api.trade;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import site.stocktrading.StockTradingApplication;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;
import site.stocktrading.api.trade.exception.TradeException;
import site.stocktrading.api.trade.service.TradeService;

@ActiveProfiles("test")
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
		assertThat(actual).isNull();
	}

	@DisplayName("종목과 수량이 주어지고 수량이 0인 경우에 예외를 발생시킨다")
	@Test
	void givenStockAndQuantity_whenBuyStockWithNotEnoughQuantity_thenThrowException() {
		// given
		Stock samsung = new Stock("samsung", 50000);
		int quantity = 0;
		// when
		CompletableFuture<Order> future = service.buyStock(samsung, quantity)
			.whenComplete((order, throwable) -> {
				if (throwable != null) {
					throw new TradeException("fail trade", throwable);
				}
			});
		// then
		Throwable throwable = catchThrowable(future::join);
		assertThat(throwable)
			.isInstanceOf(CompletionException.class)
			.hasMessage("java.lang.IllegalArgumentException: Quantity can not be negative, quantity=0");
	}

	@DisplayName("종목과 수량이 주어지고 수량이 0인 경우에 예외를 처리하여 null을 반환")
	@Test
	void givenStockAndQuantity_whenBuyStockWithNotEnoughQuantity_thenReturnNullUsingHandle() {
		// given
		Stock samsung = new Stock("samsung", 50000);
		int quantity = 0;
		// when
		CompletableFuture<Order> future = service.buyStock(samsung, quantity)
			.whenComplete((order, throwable) -> {
				if (throwable != null) {
					throw new TradeException("fail trade", throwable);
				}
			})
			.handle((order, throwable) -> {
				if (throwable != null) {
					return null;
				}
				return order;
			});
		// then
		assertThat(future.join()).isNull();
	}

	@DisplayName("종목과 수량이 주어지고 수량이 0인 경우에 null을 반환한다")
	@Test
	void givenStockAndQuantity_whenBuyStockWithNotEnoughQuantity_thenReturnNullOrder() {
		// given
		Stock samsung = new Stock("samsung", 50000);
		int quantity = 0;
		// when
		CompletableFuture<Order> future = service.buyStock(samsung, quantity)
			.exceptionally(throwable -> null);

		// then
		assertThat(future.join()).isNull();
	}
}
