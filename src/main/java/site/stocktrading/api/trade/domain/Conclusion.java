package site.stocktrading.api.trade.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Conclusion {
	private final int quantity; // 체결 주문 수량
	private final int price; // 체결 가격

	public Conclusion(int quantity, int price) {
		this.quantity = quantity;
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}
}
