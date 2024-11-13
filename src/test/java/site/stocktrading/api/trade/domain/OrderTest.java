package site.stocktrading.api.trade.domain;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import site.stocktrading.api.account.domain.Account;
import site.stocktrading.api.stock.domain.Stock;

class OrderTest {

	@DisplayName("체결 주문 수량을 계산한다")
	@ParameterizedTest
	@MethodSource(value = "quantitySource")
	void calFilledQuantity(int buyQuantity, int sellQuantity, int expected) {
		// given
		Account buyer = new Account(1L);
		Stock samsung = new Stock("삼성전자보통주", 50000);
		LocalDateTime buyOrderTime = LocalDateTime.of(2024, 11, 13, 12, 0, 0);
		Order buyOrder = Order.buy(buyer, samsung, buyQuantity, buyOrderTime);

		Account seller = new Account(2L);
		LocalDateTime sellOrderTime = LocalDateTime.of(2024, 11, 13, 11, 0, 0);
		Order sellOrder = Order.sell(seller, samsung, sellQuantity, sellOrderTime);
		// when
		int actual = sellOrder.calFilledQuantity(buyOrder);
		// then
		Assertions.assertThat(actual).isEqualTo(expected);
	}

	public static Stream<Arguments> quantitySource() {
		return Stream.of(
			Arguments.of(5, 5, 5),
			Arguments.of(5, 7, 5),
			Arguments.of(7, 5, 5)
		);
	}
}
