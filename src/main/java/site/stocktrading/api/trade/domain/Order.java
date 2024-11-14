package site.stocktrading.api.trade.domain;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.lang.NonNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import site.stocktrading.api.account.domain.Account;
import site.stocktrading.api.broker.domain.OrderBook;
import site.stocktrading.api.stock.domain.Stock;

@EqualsAndHashCode
public class Order {
	private final Account account;
	private final Stock stock;
	private final int quantity;
	private final int price;
	private final LocalDateTime time;
	private final Type type;

	/**
	 * 두 주문을 이용하여 체결 정보를 생성하여 반환
	 * - this는 매도 주문, 매개 변수의 order는 매수 주문이어야 한다
	 * - 체결 가격은 매도 주문의 가격(this.price) 기준으로 한다
	 * @param order the order
	 * @return the conclusion
	 */
	public Conclusion createConclusion(Order order) {
		int filledQuantity = calFilledQuantity(order);
		return new Conclusion(filledQuantity, this.price);
	}

	public enum Type {
		BUY,
		SELL;
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

	private static Order of(Account account, Stock stock, int quantity, int price, LocalDateTime time, Type type) {
		if (type == Type.BUY) {
			return buy(account, stock, quantity, price, time);
		}
		return sell(account, stock, quantity, price, time);
	}

	public void acceptOrderBy(Map<Stock, OrderBook> orderBooks) {
		orderBooks.computeIfAbsent(stock, OrderBook::new).addOrder(this);
	}

	/**
	 * 거래를 체결하고 남은 주문을 반환
	 *
	 * @param trade the trade
	 * @return 체결하고 남은 주문 수량을 가진 주문(Order)
	 */
	public Optional<Order> fulfill(Trade trade) {
		if (this.isFulfilled(trade)) {
			return Optional.empty();
		}
		return Optional.of(this.minusQuantity(trade.getQuantity()));
	}

	public boolean isFulfilled(Trade trade) {
		return this.compareQuantity(trade) == 0;
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

	public Order minusQuantity(int quantity) {
		int minusQuantity = this.quantity - quantity;
		return of(account, stock, minusQuantity, price, time, type);
	}

	private int compareQuantity(Trade trade) {
		return Integer.compare(this.quantity, trade.getQuantity());
	}

	public int comparePrice(Order order) {
		return Integer.compare(this.price, order.price);
	}

	public int compareTime(Order order) {
		return this.time.compareTo(order.time);
	}

	public int getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return String.format("(account=%s, stock=%s, quantity=%d, price=%d, orderTime=%s)", account, stock, quantity,
			price, time);
	}
}
