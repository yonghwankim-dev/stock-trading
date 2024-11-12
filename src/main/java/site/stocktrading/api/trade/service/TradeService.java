package site.stocktrading.api.trade.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import site.stocktrading.api.stock.domain.Stock;

@Service
public class TradeService {
	public CompletableFuture<String> buyStock(Stock stock, int quantity) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				// 비즈니스 로직: 예시로 1초 지연
				TimeUnit.SECONDS.sleep(1);
				System.out.println("Buying " + quantity + " shares of " + stock);
				// 주문 처리 성공
				return "Successfully bought " + quantity + " shares of " + stock;
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return "Buy order for " + stock + " failed";
			}
		});
	}
}
