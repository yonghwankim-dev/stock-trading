package site.stocktrading.api.trade.domain;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import site.stocktrading.api.account.domain.Account;
import site.stocktrading.api.stock.domain.Stock;

class OrderTest {

	@DisplayName("체결 주문 수량을 계산한다")
	@Test
	void calFilledQuantity() {
		// given
		Account buyer = new Account(1L);
		Stock samsung = new Stock("삼성전자보통주", 50000);
		LocalDateTime buyOrderTime = LocalDateTime.of(2024, 11, 13, 12, 0, 0);
		Order buyOrder = Order.buy(buyer, samsung, 5, buyOrderTime);

		Account seller = new Account(2L);
		LocalDateTime sellOrderTime = LocalDateTime.of(2024, 11, 13, 11, 0, 0);
		Order sellOrder = Order.sell(seller, samsung, 7, sellOrderTime);
		// when
		int actual = sellOrder.calFilledQuantity(buyOrder);
		// then
		Assertions.assertThat(actual).isEqualTo(5);
	}
}
