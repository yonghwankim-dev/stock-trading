package site.stocktrading.api.broker.domain;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import site.stocktrading.api.account.domain.Account;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;
import site.stocktrading.api.trade.domain.Trade;
import site.stocktrading.global.util.time.TimeService;

class MarketTest {

	private Market market;
	private Broker broker;

	@BeforeEach
	void setUp() {
		market = new Market();
		TimeService timeService = Mockito.mock(TimeService.class);
		broker = new Broker(market, timeService);
	}

	@DisplayName("여러개의 매수, 매도주문이 존재하고 거래 체결 매칭시 조건에 만족하는 거래를 체결한다")
	@Test
	void givenOrders_whenAttemptTrade_thenReturnTrade() {
		// given
		Account buyer = new Account(1L);
		Stock samsung = new Stock("삼성전자보통주", 50000);
		LocalDateTime buyOrderTime = LocalDateTime.of(2024, 11, 13, 12, 0, 0);
		Order buyOrder = Order.buy(buyer, samsung, 5, buyOrderTime);

		Account seller = new Account(2L);
		LocalDateTime sellOrderTime = LocalDateTime.of(2024, 11, 13, 11, 0, 0);
		Order sellOrder = Order.sell(seller, samsung, 7, sellOrderTime);

		market.acceptOrder(buyOrder);
		market.acceptOrder(sellOrder);
		// when
		Trade actual = market.attemptTrade();
		// then
		Trade expected = new Trade(buyOrder, sellOrder);
		Assertions.assertThat(actual).isEqualTo(expected);
	}
}
