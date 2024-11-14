package site.stocktrading.api.trade.domain;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import site.stocktrading.api.account.domain.Account;
import site.stocktrading.api.stock.domain.Stock;

@EqualsAndHashCode
public class Order {
	private final Account account;
	private final Stock stock;
	private final int quantity;
	private final int price;
	private final LocalDateTime time;
	private final Type type;

	public enum Type {
		BUY,
		SELL
	}

	@Builder(access = AccessLevel.PRIVATE)
	private Order(@NonNull Account account, @NonNull Stock stock, int quantity, int price, @NonNull LocalDateTime time,
		@NonNull Type type) {
		this.account = account;
		this.stock = stock;
		this.quantity = quantity;
		this.price = price;
		this.time = time;
		this.type = type;

		if (this.quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be positive, quantity=" + quantity);
		}
		if (this.price <= 0) {
			throw new IllegalArgumentException("Price must be negative, price=" + price);
		}
	}

	public static Order buy(Account account, Stock stock, int quantity, int price, LocalDateTime time) {
		return Order.builder()
			.account(account)
			.stock(stock)
			.quantity(quantity)
			.price(price)
			.time(time)
			.type(Type.BUY)
			.build();
	}

	public static Order sell(Account account, Stock stock, int quantity, int price, LocalDateTime time) {
		return Order.builder()
			.account(account)
			.stock(stock)
			.quantity(quantity)
			.price(price)
			.time(time)
			.type(Type.SELL)
			.build();
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

	public int comparePrice(Order order) {
		return Integer.compare(this.price, order.price);
	}

	public int compareTime(Order order) {
		return this.time.compareTo(order.time);
	}

	@Override
	public String toString() {
		return String.format("(account=%s, stock=%s, quantity=%s, tradeTime=%s)", account, stock, quantity, time);
	}
}
