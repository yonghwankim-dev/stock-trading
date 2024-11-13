package site.stocktrading.api.trade.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Trade {
	private final Order buyOrder;
	private final Order sellOrder;
	private final int filledQuantity; // 체결 주문 수량

	public Trade(Order buyOrder, Order sellOrder, int filledQuantity) {
		this.buyOrder = buyOrder;
		this.sellOrder = sellOrder;
		this.filledQuantity = filledQuantity;
	}
	
	@Override
	public String toString() {
		return String.format("(buyOrder=%s, sellOrder=%s)", buyOrder, sellOrder);
	}
}
