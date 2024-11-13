package site.stocktrading.api.trade.service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;
import site.stocktrading.api.trade.domain.Trade;
import site.stocktrading.global.util.delay.DelayService;
import site.stocktrading.global.util.time.TimeService;

@Service
@RequiredArgsConstructor
public class TradeService {

	private final DelayService delayService;
	private final TimeService timeService;

	public CompletableFuture<Order> buyStock(Stock stock, int quantity) {
		return CompletableFuture.supplyAsync(() -> {
			// 비즈니스 로직: 예시로 1초~3초 랜덤 지연
			delayService.delayRandomSecond(1, 3);
			// 주문 처리 성공
			LocalDateTime tradeTime = timeService.now();
			return Order.buy(stock, quantity, tradeTime);
		});
	}

	public CompletableFuture<Order> sellStock(Stock stock, int quantity) {
		return CompletableFuture.supplyAsync(() -> {
			// 비즈니스 로직: 예시로 1초~3초 지연
			delayService.delayRandomSecond(1, 3);
			// 주문 처리 성공
			LocalDateTime tradeTime = timeService.now();
			return Order.sell(stock, quantity, tradeTime);
		});
	}

	// 매수와 매도 주문을 비동기적으로 처리 후 결과를 받아오는 예시
	public CompletableFuture<Trade> processOrders(Stock stock, int quantity) {
		CompletableFuture<Order> buyFuture = buyStock(stock, quantity);
		CompletableFuture<Order> sellFuture = sellStock(stock, quantity);

		return CompletableFuture.allOf(buyFuture, sellFuture).thenApplyAsync(unused -> {
			try {
				Order buyOrder = buyFuture.get();
				Order sellOrder = sellFuture.get();
				return new Trade(buyOrder, sellOrder);
			} catch (Exception e) {
				throw new TradeException("fail trade", e);
			}
		});
	}
}
