package site.stocktrading.api.trade.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Trade {
	private final Order buyOrder;
	private final Order sellOrder;
	private final Conclusion conclusion; // 체결 정보

	private Trade(Order buyOrder, Order sellOrder, Conclusion conclusion) {
		this.buyOrder = buyOrder;
		this.sellOrder = sellOrder;
		this.conclusion = conclusion;
	}

	public static Trade filled(Order buyOrder, Order sellOrder) {
		int quantity = sellOrder.calFilledQuantity(buyOrder);
		int price = sellOrder.getPrice(); // 매도 주문에 맞추어 가격 체결
		Conclusion conclusion = new Conclusion(quantity, price);
		return new Trade(buyOrder, sellOrder, conclusion);
	}

	public int getQuantity() {
		return conclusion.getQuantity();
	}

	@Override
	public String toString() {
		return String.format("(buyOrder=%s, sellOrder=%s)", buyOrder, sellOrder);
	}
}
