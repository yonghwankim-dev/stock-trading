package site.stocktrading.api.broker.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import site.stocktrading.api.account.domain.Account;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;
import site.stocktrading.global.util.time.TimeService;

class BrokerTest {

	private TimeService timeService;
	private Broker broker;

	@BeforeEach
	void setUp() {
		timeService = Mockito.mock(TimeService.class);
		Market market = new Market();
		broker = new Broker(market, timeService);
	}

	@DisplayName("브로커에게서 종목을 매수 주문한다")
	@Test
	void orderBuyStock() {
		// given
		Account account = new Account(1L);
		int price = 50000;
		Stock samsung = new Stock("삼성전자보통주", price);
		int quantity = 5;
		LocalDateTime orderTime = LocalDateTime.of(2024, 11, 13, 12, 0, 0);
		given(timeService.now()).willReturn(orderTime);

		// when
		Order actual = broker.orderBuyStock(account, samsung, quantity, price);

		// then
		Order expected = Order.buy(account, samsung, quantity, price, orderTime);
		assertThat(actual).isEqualTo(expected);
	}

	@DisplayName("브로커에게 종목을 매도 주문한다")
	@Test
	void orderSellStock() {
		// given
		Account account = new Account(1L);
		int price = 50000;
		Stock samsung = new Stock("삼성전자보통주", price);
		int quantity = 5;
		LocalDateTime orderTime = LocalDateTime.of(2024, 11, 13, 12, 0, 0);
		given(timeService.now()).willReturn(orderTime);

		// when
		Order actual = broker.orderSellStock(account, samsung, quantity, price);
		// then
		Order expected = Order.sell(account, samsung, quantity, price, orderTime);
		assertThat(actual).isEqualTo(expected);
	}
}
