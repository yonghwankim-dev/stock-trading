package site.stocktrading.api.trade.domain;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import site.stocktrading.api.stock.domain.Stock;

@EqualsAndHashCode
public class Order {
	private final Stock stock;
	private final int quantity;
	private final LocalDateTime tradeTime;
	private final Type type;

	private enum Type {
		BUY,
		SELL
	}

	private Order(Stock stock, int quantity, LocalDateTime tradeTime, Type type) {
		this.stock = stock;
		this.quantity = quantity;
		this.tradeTime = tradeTime;
		this.type = type;

		if (this.quantity <= 0) {
			throw new IllegalArgumentException("Quantity can not be negative, quantity=" + quantity);
		}
	}

	public static Order buy(Stock stock, int quantity, LocalDateTime tradeTime) {
		return new Order(stock, quantity, tradeTime, Type.BUY);
	}

	public static Order sell(Stock stock, int quantity, LocalDateTime tradeTime) {
		return new Order(stock, quantity, tradeTime, Type.SELL);
	}

	@Override
	public String toString() {
		return String.format("(stock=%s, quantity=%s, tradeTime=%s)", stock, quantity, tradeTime);
	}
}
