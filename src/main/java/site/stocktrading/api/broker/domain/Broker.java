package site.stocktrading.api.broker.domain;

import java.time.LocalDateTime;

import site.stocktrading.api.account.domain.Account;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;
import site.stocktrading.global.util.time.TimeService;

public class Broker {

	private final TimeService timeService;

	public Broker(TimeService timeService) {
		this.timeService = timeService;
	}

	public Order orderBuyStock(Account buyer, Stock stock, int quantity) {
		LocalDateTime orderTime = timeService.now();
		return Order.buy(buyer, stock, quantity, orderTime);
	}
}
