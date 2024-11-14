package site.stocktrading.api.broker.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;

public class OrderBook {
	private static final Comparator<Order> buyOrdersComp;
	private static final Comparator<Order> sellOrdersComp;

	static {
		Comparator<Order> comparePrice = Order::comparePrice;
		buyOrdersComp = comparePrice.reversed().thenComparing(Order::compareTime);

		comparePrice = Order::comparePrice;
		sellOrdersComp = comparePrice.thenComparing(Order::compareTime);
	}

	private final Stock stock;
	private final Queue<Order> buyOrders; // 가격 내림차순-주문 시간 오름차순 정렬
	private final Queue<Order> sellOrders; // 가격 오름차순-주문 시간 오름차순 정렬

	public OrderBook(Stock stock) {
		this.stock = stock;
		this.buyOrders = new PriorityQueue<>(buyOrdersComp);
		this.sellOrders = new PriorityQueue<>(sellOrdersComp);
	}

	public void addOrder(Order order) {
		if (order.isBuyOrder()) {
			buyOrders.offer(order);
		} else {
			sellOrders.offer(order);
		}
	}

	public List<Order> findBuyOrders() {
		List<Order> result = new ArrayList<>(buyOrders);
		result.sort(buyOrdersComp);
		return Collections.unmodifiableList(result);
	}

	public List<Order> findSellOrders() {
		List<Order> result = new ArrayList<>(sellOrders);
		result.sort(sellOrdersComp);
		return Collections.unmodifiableList(result);
	}

	public Optional<Order> getTopBuyOrder() {
		return Optional.ofNullable(buyOrders.peek());
	}

	public Optional<Order> getTopSellOrder() {
		return Optional.ofNullable(sellOrders.peek());
	}

	public void removeTopBuyOrder() {
		buyOrders.poll();
	}

	public void removeTopSellOrder() {
		sellOrders.poll();
	}

	@Override
	public String toString() {
		return String.format("(stock=%s, buyOrders=%s, sellOrders=%s)", stock, buyOrders, sellOrders);
	}
}
