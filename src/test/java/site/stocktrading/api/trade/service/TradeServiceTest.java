package site.stocktrading.api.trade.service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;
import site.stocktrading.api.trade.domain.Trade;
import site.stocktrading.global.util.delay.DelayService;
import site.stocktrading.global.util.time.TimeService;

@SpringBootTest
class TradeServiceTest {

	@Autowired
	private TradeService service;

	@MockBean
	private DelayService delayService;

	@SpyBean
	private TimeService timeService;

	@BeforeEach
	void setUp() {
		BDDMockito.willDoNothing()
			.given(delayService)
			.delayRandomSecond(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
	}

	@DisplayName("종목이 주어지고 매수를 하는 경우에 비동기를 반환한다")
	@Test
	void givenStock_whenBuyStock_thenReturnFuture() {
		// given
		Stock samsung = new Stock("samsung", 50000);
		int quantity = 10;
		LocalDateTime tradeTime = LocalDateTime.of(2024, 11, 12, 12, 0, 0);
		BDDMockito.given(timeService.now()).willReturn(tradeTime);
		// when
		CompletableFuture<Order> future = service.buyStock(samsung, quantity);
		// then
		Order actual = future.join();
		Order expected = Order.buy(samsung, quantity, tradeTime);
		Assertions.assertThat(actual).isEqualTo(expected);
	}

	@DisplayName("0포함 음수 주식 개수가 주어지고 매수를 하는 경우에 거래를 취소한다")
	@Test
	void givenNegativeQuantity_whenBuyStock_thenCancelTrade() {
		// given
		Stock samsung = new Stock("samsung", 50000);
		int quantity = 0;
		LocalDateTime tradeTime = LocalDateTime.of(2024, 11, 12, 12, 0, 0);
		BDDMockito.given(timeService.now()).willReturn(tradeTime);
		// when
		CompletableFuture<Order> future = service.buyStock(samsung, quantity);
		// then
		Throwable throwable = Assertions.catchThrowable(future::join);
		Assertions.assertThat(throwable)
			.isInstanceOf(CompletionException.class)
			.hasMessage("java.lang.IllegalArgumentException: Quantity can not be negative, quantity=0");
	}

	@DisplayName("매수, 매도 주문이 주어지고 주문이 성공하면 거래를 체결한다")
	@Test
	void givenOrders_whenProcessOrders_thenReturnTrade() {
		// given
		Stock samsung = new Stock("samsung", 50000);
		LocalDateTime tradeTime = LocalDateTime.of(2024, 11, 12, 12, 0, 0);
		BDDMockito.given(timeService.now()).willReturn(tradeTime);
		// when
		CompletableFuture<Trade> future = service.processOrders(samsung, 10);

		// then
		Trade actual = future.join();
		Trade expected = new Trade(Order.buy(samsung, 10, tradeTime), Order.sell(samsung, 10, tradeTime));
		Assertions.assertThat(actual).isEqualTo(expected);
	}

	@DisplayName("매수, 매도 주문이 주어지고 주문 개수가 0개인 경우 거래를 체결되지 않는다")
	@Test
	void givenOrders_whenProcessOrdersWithZeroQuantity_thenNotTrade() {
		// given
		Stock samsung = new Stock("samsung", 50000);
		LocalDateTime tradeTime = LocalDateTime.of(2024, 11, 12, 12, 0, 0);
		BDDMockito.given(timeService.now()).willReturn(tradeTime);
		// when
		Throwable throwable = Assertions.catchThrowable(() -> service.processOrders(samsung, 0));
		// then
		Assertions.assertThat(throwable)
			.isInstanceOf(CompletionException.class)
			.hasMessage("java.lang.IllegalArgumentException: Quantity can not be negative, quantity=0");
	}
}
