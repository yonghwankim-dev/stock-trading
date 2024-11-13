package site.stocktrading.api.broker.domain;

import lombok.extern.slf4j.Slf4j;
import site.stocktrading.api.trade.domain.Order;

@Slf4j
public class Market {
	public void acceptOrder(Order order) {
		log.info("accept the Order, order={}", order);
	}
}
