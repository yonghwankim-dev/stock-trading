package site.stocktrading.api.trade.exception;

public class TradeException extends RuntimeException {

	public TradeException(String message, Throwable cause) {
		super(message, cause);
	}
}
