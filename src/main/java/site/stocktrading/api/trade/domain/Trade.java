package site.stocktrading.api.trade.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Trade {
	private final Order buyOrder;
	private final Order sellOrder;
	private final int quantity; // 체결 주문 수량
	private final int price; // 체결 가격
	private final Conclusion conclusion; // 체결 정보

	private Trade(Order buyOrder, Order sellOrder, int quantity, int price, Conclusion conclusion) {
		this.buyOrder = buyOrder;
		this.sellOrder = sellOrder;
		this.quantity = quantity;
		this.price = price;
		this.conclusion = conclusion;
	}

	public static Trade filled(Order buyOrder, Order sellOrder) {
		int quantity = sellOrder.calFilledQuantity(buyOrder);
		int price = sellOrder.getPrice(); // 매도 주문에 맞추어 가격 체결
		Conclusion conclusion = new Conclusion(quantity, price);
		return new Trade(buyOrder, sellOrder, quantity, price, conclusion);
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public String toString() {
		return String.format("(buyOrder=%s, sellOrder=%s)", buyOrder, sellOrder);
	}
}
