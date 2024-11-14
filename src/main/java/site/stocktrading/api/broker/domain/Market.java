package site.stocktrading.api.broker.domain;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;
import site.stocktrading.api.trade.domain.Trade;

@Slf4j
public class Market {

	private final Map<Stock, OrderBook> orderBooks = new ConcurrentHashMap<>();

	public void acceptOrder(Order order) {
		order.acceptOrderBy(orderBooks);
	}

	/**
	 * 조건을 만족하는 매수, 매도 주문이 있다면 거래를 체결한다
	 *거래 체결 조건
	 * - 매수, 매도 주문이 존재해야 한다
	 * - 거래 체결시 매수, 매도 주문의 종목이 동일해야 한다
	 * - 거래 체결시 하나의 매수 주문과 2개 이상의 매도 주문이 존재하는 경우 주문 시간이 빠른 순서대로 체결된다
	 * @return Optional<Trade> 체결된 거래
	 */
	public Optional<Trade> attemptTrade(Stock stock) {
		OrderBook orderBook = orderBooks.get(stock);
		if (orderBook == null) {
			return Optional.empty();
		}

		Optional<Order> topBuyOrder = orderBook.getTopBuyOrder();
		Optional<Order> topSellOrder = orderBook.getTopSellOrder();

		if (topBuyOrder.isPresent() && topSellOrder.isPresent()) {
			Order buyOrder = topBuyOrder.get();
			Order sellOrder = topSellOrder.get();

			if (buyOrder.comparePrice(sellOrder) >= 0) {
				Trade trade = Trade.filled(buyOrder, sellOrder);

				// 체결된 수량만큼 매수 주문 수량 감소
				orderBook.removeTopBuyOrder();
				if (!buyOrder.isFulfilled(trade)) {
					buyOrder.fulfill(trade).ifPresent(orderBook::addOrder);
				}

				// 체결된 수량만큼 매도 주문 수량 감소
				orderBook.removeTopSellOrder();
				if (!sellOrder.isFulfilled(trade)) {
					sellOrder.fulfill(trade).ifPresent(orderBook::addOrder);
				}

				return Optional.of(Trade.filled(buyOrder, sellOrder));
			}
		}

		return Optional.empty();
	}

	public Optional<OrderBook> getOrderBook(Stock stock) {
		return Optional.ofNullable(orderBooks.get(stock));
	}
}
