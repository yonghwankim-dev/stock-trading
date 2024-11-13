package site.stocktrading.api.broker.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import site.stocktrading.api.trade.domain.Order;
import site.stocktrading.api.trade.domain.Trade;

@Slf4j
public class Market {

	private final List<Order> orders = new ArrayList<>();

	public void acceptOrder(Order order) {
		orders.add(order);
		log.info("accept the Order, order={}", order);
	}

	/**
	 * 조건을 만족하는 매수, 매도 주문이 있다면 거래를 체결한다
	 * 거래 체결 조건
	 * - 매수, 매도 주문이 존재해야 한다
	 * - 거래 체결시 매수, 매도 주문의 종목이 동일해야 한다
	 * - 매수 주문 수량이 매도 주문 수량보다 이하여야 한다
	 * - 거래 체결시 하나의 매수 주문과 2개 이상의 매도 주문이 존재하는 경우 주문 시간이 빠른 순서대로 체결된다
	 */
	public Trade attemptTrade() {
		return null;
	}
}
