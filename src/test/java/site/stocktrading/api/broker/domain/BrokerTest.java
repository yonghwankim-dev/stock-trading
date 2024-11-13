package site.stocktrading.api.broker.domain;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import site.stocktrading.api.account.domain.Account;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;

class BrokerTest {

	@DisplayName("브로커에게서 종목을 매수 주문한다")
	@Test
	void orderBuyStock() {
		// given
		Broker broker = new Broker();
		// when
		Order order = broker.orderBuyStock();
		// then
		Stock samsung = new Stock("삼성전자보통주", 50000);
		int quantity = 5;
		LocalDateTime tradeTime = LocalDateTime.of(2024, 11, 13, 12, 0, 0);
		Order expected = Order.buy(new Account(1L), samsung, quantity, tradeTime);
		Assertions.assertThat(order).isEqualTo(expected);
	}

}
