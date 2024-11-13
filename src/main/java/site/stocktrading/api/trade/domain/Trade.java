package site.stocktrading.api.trade.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Trade {
	private Order buyOrder;
	private Order sellOrder;

	public Trade(Order buyOrder, Order sellOrder) {
		this.buyOrder = buyOrder;
		this.sellOrder = sellOrder;
	}
}
