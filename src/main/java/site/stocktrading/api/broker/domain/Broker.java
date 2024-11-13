package site.stocktrading.api.broker.domain;

import java.time.LocalDateTime;

import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;

public class Broker {
	public Order orderBuyStock() {
		Stock stock = new Stock("삼성전자보통주", 50000);
		int quantity = 5;
		LocalDateTime tradeTime = LocalDateTime.of(2024, 11, 13, 12, 0, 0);
		return Order.buy(stock, quantity, tradeTime);
	}
}
