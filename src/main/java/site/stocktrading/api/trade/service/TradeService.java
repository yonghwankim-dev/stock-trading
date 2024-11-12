package site.stocktrading.api.trade.service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.trade.domain.Trade;
import site.stocktrading.global.util.delay.DelayService;
import site.stocktrading.global.util.time.TimeService;

@Service
@RequiredArgsConstructor
public class TradeService {

	private final DelayService delayService;
	private final TimeService timeService;

	public CompletableFuture<Trade> buyStock(Stock stock, int quantity) {
		return CompletableFuture.supplyAsync(() -> {
			// 비즈니스 로직: 예시로 1초~3초 랜덤 지연
			delayService.delayRandomSecond(1, 3);
			// 주문 처리 성공
			LocalDateTime tradeTime = timeService.now();
			return Trade.buy(stock, quantity, tradeTime);
		});
	}

	public CompletableFuture<Trade> sellStock(Stock stock, int quantity) {
		return CompletableFuture.supplyAsync(() -> {
			// 비즈니스 로직: 예시로 1초~3초 지연
			delayService.delayRandomSecond(1, 3);
			// 주문 처리 성공
			LocalDateTime tradeTime = timeService.now();
			return Trade.sell(stock, quantity, tradeTime);
		});
	}
}
