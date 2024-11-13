package site.stocktrading.api.trade.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Trade {
	private final Order buyOrder;
	private final Order sellOrder;

	public Trade(Order buyOrder, Order sellOrder) {
		this.buyOrder = buyOrder;
		this.sellOrder = sellOrder;
	}

	@Override
	public String toString() {
		return String.format("(buyOrder=%s, sellOrder=%s)", buyOrder, sellOrder);
	}
}
