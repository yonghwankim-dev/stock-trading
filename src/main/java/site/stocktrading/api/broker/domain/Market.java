package site.stocktrading.api.broker.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import site.stocktrading.api.trade.domain.Order;

@Slf4j
public class Market {

	private final List<Order> buyOrders = new ArrayList<>();

	public void acceptOrder(Order order) {
		boolean add = buyOrders.add(order);
		log.info("accept the Order, order={}, add={}", order, add);
	}
}
