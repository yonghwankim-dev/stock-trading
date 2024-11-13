package site.stocktrading.api.trade.service;

public class TradeException extends RuntimeException {
	
	public TradeException(String message, Throwable cause) {
		super(message, cause);
	}
}
