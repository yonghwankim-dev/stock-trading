package site.stocktrading.api.broker.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import site.stocktrading.api.trade.domain.Order;

@Slf4j
public class Market {

	private final List<Order> orders = new ArrayList<>();

	public void acceptOrder(Order order) {
		orders.add(order);
		log.info("accept the Order, order={}", order);
	}
}
