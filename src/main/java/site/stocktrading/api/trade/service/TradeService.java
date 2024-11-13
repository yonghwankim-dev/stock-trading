package site.stocktrading.api.trade.service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Order;
import site.stocktrading.api.trade.domain.Trade;
import site.stocktrading.api.trade.exception.OrderException;
import site.stocktrading.api.trade.exception.TradeException;
import site.stocktrading.global.util.delay.DelayService;
import site.stocktrading.global.util.time.TimeService;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeService {

	private final DelayService delayService;
	private final TimeService timeService;

	public CompletableFuture<Order> buyStock(Stock stock, int quantity) {
		return CompletableFuture.supplyAsync(() -> {
			log.info("buyStock: stock={}, quantity={}", stock, quantity);
			// 비즈니스 로직: 예시로 1초~3초 랜덤 지연
			delayService.delayRandomSecond(1, 3);
			// 주문 처리 성공
			LocalDateTime tradeTime = timeService.now();
			return Order.buy(stock, quantity, tradeTime);
		});
	}

	public CompletableFuture<Order> sellStock(Stock stock, int quantity) {
		return CompletableFuture.supplyAsync(() -> {
			log.info("sellStock: stock={}, quantity={}", stock, quantity);
			// 비즈니스 로직: 예시로 1초~3초 지연
			delayService.delayRandomSecond(1, 3);
			// 주문 처리 성공
			LocalDateTime tradeTime = timeService.now();
			return Order.sell(stock, quantity, tradeTime);
		});
	}

	// 매수와 매도 주문을 비동기적으로 처리 후 결과를 받아오는 예시
	public CompletableFuture<Trade> processOrders(Stock stock, int quantity) {
		CompletableFuture<Order> buyFuture = buyStock(stock, quantity)
			.exceptionally(throwable -> {
				throw new OrderException("Buy Operation Failed", throwable);
			});
		CompletableFuture<Order> sellFuture = sellStock(stock, quantity)
			.exceptionally(throwable -> {
				throw new OrderException("Sell Operation Failed", throwable);
			});

		return buyFuture.thenCombine(sellFuture, Trade::new)
			.exceptionally(throwable -> {
				throw new TradeException("Trade failed due to an operation failure", throwable);
			});
	}
}
