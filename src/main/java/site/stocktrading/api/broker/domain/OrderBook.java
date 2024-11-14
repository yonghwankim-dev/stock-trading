package site.stocktrading.api.broker.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;

public class OrderBook {
	private static final Comparator<Order> buyOrdersComp;
	private static final Comparator<Order> sellOrdersComp;

	static {
		Comparator<Order> comparePrice = Order::comparePrice;
		comparePrice.reversed().thenComparing(Order::compareTime);
		buyOrdersComp = comparePrice;

		comparePrice = Order::comparePrice;
		comparePrice.thenComparing(Order::compareTime);
		sellOrdersComp = comparePrice;
	}

	private final Stock stock;
	private final Queue<Order> buyOrders; // 높은 가격-주문 시간 우선
	private final Queue<Order> sellOrders; // 낮은 가격-주문 시간 우선

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
		return new ArrayList<>(buyOrders);
	}
}
