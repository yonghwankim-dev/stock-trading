package site.stocktrading.api.trade.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import site.stocktrading.api.account.domain.Account;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;
import site.stocktrading.api.trade.domain.Trade;
import site.stocktrading.global.util.delay.DelayService;
import site.stocktrading.global.util.time.TimeService;

@ActiveProfiles("test")
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
		willDoNothing()
			.given(delayService)
			.delayRandomSecond(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
	}

	@DisplayName("종목이 주어지고 매수를 하는 경우에 비동기를 반환한다")
	@Test
	void givenStock_whenBuyStock_thenReturnFuture() {
		// given
		int price = 50000;
		Stock samsung = new Stock("samsung", price);
		int quantity = 10;
		LocalDateTime tradeTime = LocalDateTime.of(2024, 11, 12, 12, 0, 0);
		given(timeService.now()).willReturn(tradeTime);

		// when
		CompletableFuture<Order> future = service.buyStock(samsung, quantity, price);

		// then
		Order actual = future.join();
		Order expected = Order.buy(new Account(1L), samsung, quantity, price, tradeTime);
		assertThat(actual).isEqualTo(expected);
	}

	@DisplayName("0포함 음수 주식 개수가 주어지고 매수를 하는 경우에 거래를 취소한다")
	@Test
	void givenNegativeQuantity_whenBuyStock_thenCancelTrade() {
		// given
		int price = 50000;
		Stock samsung = new Stock("samsung", price);
		int quantity = 0;
		LocalDateTime tradeTime = LocalDateTime.of(2024, 11, 12, 12, 0, 0);
		given(timeService.now()).willReturn(tradeTime);

		// when
		CompletableFuture<Order> future = service.buyStock(samsung, quantity, price);

		// then
		Throwable throwable = catchThrowable(future::join);
		assertThat(throwable)
			.isInstanceOf(CompletionException.class)
			.hasMessage("java.lang.IllegalArgumentException: Quantity must be positive, quantity=0");
	}

	@DisplayName("매수, 매도 주문이 주어지고 주문이 성공하면 거래를 체결한다")
	@Test
	void givenOrders_whenProcessOrders_thenReturnTrade() {
		// given
		int price = 50000;
		Stock samsung = new Stock("samsung", price);
		LocalDateTime tradeTime = LocalDateTime.of(2024, 11, 12, 12, 0, 0);
		given(timeService.now()).willReturn(tradeTime);

		// when
		Trade actual = service.processOrders(samsung, 10, price).join();

		// then
		Order buyOrder = Order.buy(new Account(1L), samsung, 10, price, tradeTime);
		Order sellOrder = Order.sell(new Account(1L), samsung, 10, price, tradeTime);
		Trade expected = Trade.filled(buyOrder, sellOrder);
		assertThat(actual).isEqualTo(expected);
	}

	@DisplayName("매수, 매도 주문이 주어지고 주문 개수가 0개인 경우 거래를 체결되지 않는다")
	@Test
	void givenOrders_whenProcessOrdersWithZeroQuantity_thenNotTrade() {
		// given
		int price = 50000;
		Stock samsung = new Stock("samsung", price);
		LocalDateTime tradeTime = LocalDateTime.of(2024, 11, 12, 12, 0, 0);
		given(timeService.now()).willReturn(tradeTime);

		int quantity = 0;
		// when
		Throwable throwable = catchThrowable(() -> service.processOrders(samsung, quantity, price).join());

		// then
		String expected = "site.stocktrading.api.trade.exception.TradeException: Trade failed due to an operation failure";
		assertThat(throwable)
			.isInstanceOf(CompletionException.class)
			.hasMessage(expected);
	}
}
