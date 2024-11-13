package site.stocktrading.api.broker.domain;

import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
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

	@BeforeEach
	void setUp() {
		timeService = Mockito.mock(TimeService.class);
	}

	@DisplayName("브로커에게서 종목을 매수 주문한다")
	@Test
	void orderBuyStock() {
		// given
		Account account = new Account(1L);
		Broker broker = new Broker(timeService);
		Stock samsung = new Stock("삼성전자보통주", 50000);
		int quantity = 5;
		LocalDateTime orderTime = LocalDateTime.of(2024, 11, 13, 12, 0, 0);
		given(timeService.now()).willReturn(orderTime);

		// when
		Order order = broker.orderBuyStock(account, samsung, quantity);

		// then
		Order expected = Order.buy(account, samsung, quantity, orderTime);
		Assertions.assertThat(order).isEqualTo(expected);
	}

}
