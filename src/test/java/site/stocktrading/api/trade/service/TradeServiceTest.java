package site.stocktrading.api.trade.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import site.stocktrading.api.stock.domain.Stock;
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
		BDDMockito.willDoNothing().given(delayService).delay(1, ChronoUnit.SECONDS);
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
		CompletableFuture<Trade> future = service.buyStock(samsung, quantity);
		// then
		Trade actual = future.join();
		Trade expected = Trade.of(samsung, quantity, tradeTime);
		Assertions.assertThat(actual).isEqualTo(expected);
	}
}
