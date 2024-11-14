package site.stocktrading.api.broker.domain;

import java.util.PriorityQueue;
import java.util.Queue;

import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;

public class OrderBook {
	private Stock stock;
	private Queue<Order> buyOrders; // 높은 가격-주문 시간 우선
	private Queue<Order> sellOrders; // 낮은 가격-주문 시간 우선

	public OrderBook(Stock stock) {
		this.stock = stock;
		this.buyOrders = new PriorityQueue<>();
		this.sellOrders = new PriorityQueue<>();
	}
}
