package site.stocktrading.api.broker.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

	@DisplayName("매수, 매도 주문후 거래 체결 시나리오")
	@TestFactory
	Stream<DynamicTest> acceptOrderAndAttemptTrade() {
		int currentPrice = 50000;
		Stock samsung = new Stock("삼성전자보통주", currentPrice);

		Account seller = new Account(1L);
		int sellQuantity = 7;
		int sellPrice = 50000;
		LocalDateTime sellOrderTime = LocalDateTime.of(2024, 11, 13, 11, 0, 0);
		Order sellOrder = Order.sell(seller, samsung, sellQuantity, sellPrice, sellOrderTime);

		sellQuantity = 6;
		sellPrice = 51000;
		sellOrderTime = sellOrderTime.minusHours(1);
		Order sellOrder2 = Order.sell(seller, samsung, sellQuantity, sellPrice, sellOrderTime);

		Account buyer = new Account(2L);
		int buyQuantity = 5;
		int buyPrice = 50000;
		LocalDateTime buyOrderTime = LocalDateTime.of(2024, 11, 13, 12, 0, 0);
		Order buyOrder = Order.buy(buyer, samsung, buyQuantity, buyPrice, buyOrderTime);

		buyQuantity = 4;
		buyPrice = 40000;
		buyOrderTime = buyOrderTime.plusMinutes(10);
		Order buyOrder2 = Order.buy(buyer, samsung, buyQuantity, buyPrice, buyOrderTime);

		return Stream.of(
			dynamicTest("매도 주문을 접수한다", () -> {
				// given

				// when
				market.acceptOrder(sellOrder);
				market.acceptOrder(sellOrder2);
				// then
				OrderBook actualOrderBook = market.getOrderBook(samsung).orElseThrow();
				List<Order> actualSellOrders = actualOrderBook.findSellOrders();
				assertThat(actualSellOrders)
					.hasSize(2)
					.containsExactly(sellOrder, sellOrder2);
			}),
			dynamicTest("매수 주문을 접수한다", () -> {
				// given

				// when
				market.acceptOrder(buyOrder);
				market.acceptOrder(buyOrder2);
				// then
				OrderBook actualOrderBook = market.getOrderBook(samsung).orElseThrow();
				List<Order> actualBuyOrders = actualOrderBook.findBuyOrders();
				assertThat(actualBuyOrders)
					.hasSize(2)
					.containsExactly(buyOrder, buyOrder2);
			}),
			dynamicTest("거래를 체결한다", () -> {
				// given

				// when
				Optional<Trade> trade = market.attemptTrade(samsung);
				// then
				Trade expected = Trade.filled(buyOrder, sellOrder);
				assertThat(trade)
					.as("Verify returned trade")
					.contains(expected);
				OrderBook actualOrderBook = market.getOrderBook(samsung).orElseThrow();
				List<Order> actualBuyOrders = actualOrderBook.findBuyOrders();
				assertThat(actualBuyOrders)
					.as("Verify remained BuyOrders")
					.hasSize(1)
					.containsExactly(buyOrder2);

				List<Order> actualSellOrders = actualOrderBook.findSellOrders();
				LocalDateTime expectedOrderTime = LocalDateTime.of(2024, 11, 13, 11, 0, 0);
				Order expectedSellOrder = Order.sell(seller, samsung, 2, 50000, expectedOrderTime);
				assertThat(actualSellOrders)
					.as("Verify remained SellOrders")
					.hasSize(2)
					.containsExactly(expectedSellOrder, sellOrder2);
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
