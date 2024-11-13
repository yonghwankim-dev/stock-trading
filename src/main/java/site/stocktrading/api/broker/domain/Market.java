package site.stocktrading.api.broker.domain;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import site.stocktrading.api.trade.domain.Order;
import site.stocktrading.api.trade.domain.Trade;

@Slf4j
public class Market {

	private final Queue<Order> buyOrders = new PriorityQueue<>(Order::compareTime);
	private final Queue<Order> sellOrders = new PriorityQueue<>(Order::compareTime);

	public void acceptOrder(Order order) {
		if (order.isBuyOrder()) {
			buyOrders.add(order);
			log.info("accept the buyOrder, order={}", order);
		} else {
			sellOrders.add(order);
			log.info("accept the sellOrder, order={}", order);
		}
	}

	/**
	 * 조건을 만족하는 매수, 매도 주문이 있다면 거래를 체결한다
	 * 거래 체결 조건
	 * - 매수, 매도 주문이 존재해야 한다
	 * - 거래 체결시 매수, 매도 주문의 종목이 동일해야 한다
	 * - 거래 체결시 하나의 매수 주문과 2개 이상의 매도 주문이 존재하는 경우 주문 시간이 빠른 순서대로 체결된다
	 * @return Optional<Trade> 체결된 거래
	 */
	public Optional<Trade> attemptTrade() {

		Optional<Order> buyOrder = pollOrder().apply(buyOrders);
		if (buyOrder.isEmpty()) {
			return Optional.empty();
		}
		Optional<Order> sellOrder = pollOrder().apply(sellOrders);
		return sellOrder.map(order -> Trade.filled(buyOrder.get(), order));
	}

	private Function<Queue<Order>, Optional<Order>> pollOrder() {
		return queue -> Optional.ofNullable(queue.poll());
	}
}
