package site.stocktrading.api.trade.domain;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import site.stocktrading.api.account.domain.Account;
import site.stocktrading.api.stock.domain.Stock;

@EqualsAndHashCode
public class Order {
	private final Account account;
	private final Stock stock;
	private final int quantity;
	private final LocalDateTime time;
	private final Type type;

	public enum Type {
		BUY,
		SELL;

	}

	private Order(Account account, Stock stock, int quantity, LocalDateTime time, Type type) {
		this.account = account;
		this.stock = stock;
		this.quantity = quantity;
		this.time = time;
		this.type = type;

		if (this.quantity <= 0) {
			throw new IllegalArgumentException("Quantity can not be negative, quantity=" + quantity);
		}
	}

	public static Order buy(Account account, Stock stock, int quantity, LocalDateTime time) {
		return new Order(account, stock, quantity, time, Type.BUY);
	}

	public static Order sell(Account account, Stock stock, int quantity, LocalDateTime time) {
		return new Order(account, stock, quantity, time, Type.SELL);
	}

	/**
	 * 체결 주문 수량을 계산
	 *
	 * @param order 매수 주문
	 * @return 체결 주문 수량 개수
	 */
	public int calFilledQuantity(Order order) {
		return Math.min(this.quantity, order.quantity);
	}

	public boolean isBuyOrder() {
		return this.type == Type.BUY;
	}

	public int compareTime(Order order) {
		return time.compareTo(order.time);
	}

	@Override
	public String toString() {
		return String.format("(account=%s, stock=%s, quantity=%s, tradeTime=%s)", account, stock, quantity, time);
	}
}
