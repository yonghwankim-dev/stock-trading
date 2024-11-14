package site.stocktrading.api.broker.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import site.stocktrading.api.account.domain.Account;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;

class OrderBookTest {

	private OrderBook orderBook;

	@BeforeEach
	void setUp() {
		Stock stock = new Stock("삼성전자보통주", 50000);
		orderBook = new OrderBook(stock);
	}

	@DisplayName("주문을 추가한다")
	@Test
	void addOrder() {
		// given
		Account buyer = new Account(1L);
		int price = 50000;
		Stock samsung = new Stock("삼성전자보통주", price);
		LocalDateTime buyOrderTime = LocalDateTime.of(2024, 11, 13, 12, 0, 0);
		Order fiveBuyOrder = Order.buy(buyer, samsung, 5, price, buyOrderTime);

		price = 51000;
		buyOrderTime = LocalDateTime.of(2024, 11, 13, 11, 0, 0);
		Order sevenBuyOrder = Order.buy(buyer, samsung, 7, price, buyOrderTime);

		Account seller = new Account(2L);
		LocalDateTime sellOrderTime = LocalDateTime.of(2024, 11, 13, 11, 0, 0);
		price = 52000;
		Order sevenSellOrder = Order.sell(seller, samsung, 7, price, sellOrderTime);

		price = 53000;
		sellOrderTime = LocalDateTime.of(2024, 11, 13, 10, 0, 0);
		Order fiveSellOrder = Order.sell(seller, samsung, 5, price, sellOrderTime);

		// when
		orderBook.addOrder(fiveBuyOrder);
		orderBook.addOrder(sevenBuyOrder);
		orderBook.addOrder(sevenSellOrder);
		orderBook.addOrder(fiveSellOrder);

		// then
		// 가격 내림차순 - 시간 오름차순으로 정렬되었는지 확인
		List<Order> buyOrders = orderBook.findBuyOrders();
		Assertions.assertThat(buyOrders)
			.hasSize(2);
	}

}
