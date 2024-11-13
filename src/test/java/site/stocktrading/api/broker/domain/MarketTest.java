package site.stocktrading.api.broker.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

		// when
		Trade actual = market.attemptTrade();
		// then
		Assertions.assertThat(actual).isNull();
	}

}
