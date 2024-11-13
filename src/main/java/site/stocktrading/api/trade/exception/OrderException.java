package site.stocktrading.api.trade.exception;

public class OrderException extends RuntimeException {

	public OrderException(String message, Throwable cause) {
		super(message, cause);
	}
}
