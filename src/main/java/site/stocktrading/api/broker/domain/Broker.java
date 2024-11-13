package site.stocktrading.api.broker.domain;

import java.time.LocalDateTime;

import site.stocktrading.api.account.domain.Account;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;

public class Broker {
	public Order orderBuyStock(Account buyer, Stock stock) {
		int quantity = 5;
		LocalDateTime tradeTime = LocalDateTime.of(2024, 11, 13, 12, 0, 0);
		return Order.buy(buyer, stock, quantity, tradeTime);
	}
}
