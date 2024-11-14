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
		Conclusion conclusion = sellOrder.createConclusion(buyOrder);
		return new Trade(buyOrder, sellOrder, conclusion);
	}

	public int minusQuantity(int quantity) {
		return conclusion.minusQuantity(quantity);
	}

	public int compareQuantity(int quantity) {
		return conclusion.compareQuantity(quantity);
	}

	@Override
	public String toString() {
		return String.format("(buyOrder=%s, sellOrder=%s)", buyOrder, sellOrder);
	}
}
