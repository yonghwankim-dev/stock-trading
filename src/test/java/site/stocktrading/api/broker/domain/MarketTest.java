package site.stocktrading.api.broker.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import site.stocktrading.api.account.domain.Account;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;
import site.stocktrading.api.trade.domain.Trade;

class MarketTest {

	private Market market;

	@BeforeEach
	void setUp() {
		market = new Market();
	}

	@DisplayName("여러개의 매수, 매도주문이 존재하고 거래 체결 매칭시 조건에 만족하는 거래를 체결한다")
	@Test
	void givenOrders_whenAttemptTrade_thenReturnTrade() {
		// given
		Account buyer = new Account(1L);
		int quantity = 5;
		int price = 50000;
		Stock samsung = new Stock("삼성전자보통주", price);
		LocalDateTime buyOrderTime = LocalDateTime.of(2024, 11, 13, 12, 0, 0);
		Order buyOrder = Order.buy(buyer, samsung, quantity, price, buyOrderTime);

		Account seller = new Account(2L);
		quantity = 7;
		LocalDateTime sellOrderTime = LocalDateTime.of(2024, 11, 13, 11, 0, 0);
		Order sellOrder = Order.sell(seller, samsung, quantity, price, sellOrderTime);

		market.acceptOrder(buyOrder);
		market.acceptOrder(sellOrder);
		// when
		Optional<Trade> actual = market.attemptTrade(samsung);
		// then
		Trade expected = Trade.filled(buyOrder, sellOrder);
		assertThat(actual).contains(expected);
	}

	@DisplayName("매수, 매도 주문의 거래 체결 시나리오")
	@TestFactory
	Stream<DynamicTest> acceptOrderAndAttemptTrade() {
		int currentPrice = 50000;
		Stock samsung = new Stock("삼성전자보통주", currentPrice);

		return Stream.of(
			dynamicTest("매도 주문을 접수한다", () -> {
				// given
				Account seller = new Account(2L);
				int quantity = 7;
				int sellPrice = 50000;
				LocalDateTime sellOrderTime = LocalDateTime.of(2024, 11, 13, 11, 0, 0);
				Order sellOrder = Order.sell(seller, samsung, quantity, sellPrice, sellOrderTime);

				quantity = 6;
				sellPrice = 51000;
				sellOrderTime = sellOrderTime.minusHours(1);
				Order sellOrder2 = Order.sell(seller, samsung, quantity, sellPrice, sellOrderTime);
				// when
				market.acceptOrder(sellOrder);
				market.acceptOrder(sellOrder2);
				// then
				Optional<OrderBook> orderBookOpt = market.getOrderBook(samsung);
				Assertions.assertThat(orderBookOpt).isPresent();
				Assertions.assertThat(orderBookOpt.get().findSellOrders())
					.hasSize(2)
					.containsExactly(sellOrder, sellOrder2);
			}),
			dynamicTest("매수 주문을 접수한다", () -> {
				// given
				Account seller = new Account(2L);
				int quantity = 5;
				int buyPrice = 50000;
				LocalDateTime buyOrderTime = LocalDateTime.of(2024, 11, 13, 12, 0, 0);
				Order buyOrder = Order.buy(seller, samsung, quantity, buyPrice, buyOrderTime);

				quantity = 4;
				buyPrice = 40000;
				buyOrderTime = buyOrderTime.plusMinutes(10);
				Order buyOrder2 = Order.buy(seller, samsung, quantity, buyPrice, buyOrderTime);
				// when
				market.acceptOrder(buyOrder);
				market.acceptOrder(buyOrder2);
				// then
				Optional<OrderBook> orderBookOpt = market.getOrderBook(samsung);
				Assertions.assertThat(orderBookOpt).isPresent();
				Assertions.assertThat(orderBookOpt.get().findBuyOrders())
					.hasSize(2)
					.containsExactly(buyOrder, buyOrder2);
			}),
			dynamicTest("거래를 체결한다", () -> {

			})
		);
	}

	@DisplayName("매수 주문만 존재하고 매도 주문이 없다면 거래를 체결되지 않는다")
	@Test
	void givenOnlyBuyOrders_whenAttemptTrade_thenNotTrade() {
		// given
		Account buyer = new Account(1L);
		int quantity = 5;
		int price = 50000;
		Stock samsung = new Stock("삼성전자보통주", price);
		LocalDateTime buyOrderTime = LocalDateTime.of(2024, 11, 13, 12, 0, 0);
		Order buyOrder = Order.buy(buyer, samsung, quantity, price, buyOrderTime);

		market.acceptOrder(buyOrder);
		// when
		Optional<Trade> actual = market.attemptTrade(samsung);
		// then
		assertThat(actual).isEmpty();
	}
}
