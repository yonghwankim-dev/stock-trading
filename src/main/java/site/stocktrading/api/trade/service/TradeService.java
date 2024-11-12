package site.stocktrading.api.trade.service;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.global.util.delay.DelayService;

@Service
@RequiredArgsConstructor
public class TradeService {

	private final DelayService delayService;

	public CompletableFuture<String> buyStock(Stock stock, int quantity) {
		return CompletableFuture.supplyAsync(() -> {
			// 비즈니스 로직: 예시로 1초 지연
			delayService.delay(1, ChronoUnit.SECONDS);
			System.out.println("Buying " + quantity + " shares of " + stock);
			// 주문 처리 성공
			return "Successfully bought " + quantity + " shares of " + stock;
		});
	}
}
