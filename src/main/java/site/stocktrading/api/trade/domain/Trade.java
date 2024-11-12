package site.stocktrading.api.trade.domain;

import java.time.LocalDateTime;

import site.stocktrading.api.stock.domain.Stock;

public class Trade {
	private final Stock stock;
	private final int quantity;
	private final LocalDateTime tradeTime;

	public Trade(Stock stock, int quantity, LocalDateTime tradeTime) {
		this.stock = stock;
		this.quantity = quantity;
		this.tradeTime = tradeTime;

		if (this.quantity <= 0) {
			throw new IllegalArgumentException("Quantity can not be negative, quantity=" + quantity);
		}
	}

	public static Trade now(Stock stock, int quantity) {
		LocalDateTime now = LocalDateTime.now();
		return new Trade(stock, quantity, now);
	}

	@Override
	public String toString() {
		return String.format("(stock=%s, quantity=%s, tradeTime=%s)", stock, quantity, tradeTime);
	}
}
