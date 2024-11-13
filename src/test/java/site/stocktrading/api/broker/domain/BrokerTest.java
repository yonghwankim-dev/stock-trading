package site.stocktrading.api.broker.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import site.stocktrading.api.trade.domain.Order;

class BrokerTest {

	@DisplayName("브로커에게서 종목을 매수 주문한다")
	@Test
	void buy() {
		// given
		Broker broker = new Broker();
		// when
		Order order = broker.orderBuyStock();
		// then
		Assertions.assertThat(order).isNull();
	}

}
