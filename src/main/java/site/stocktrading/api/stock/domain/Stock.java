package site.stocktrading.api.stock.domain;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class Stock {
	private final String name;
	private final int price;

	public Stock saveTo(Map<String, Stock> store) {
		return store.put(name, this);
	}

	@Override
	public String toString() {
		return String.format("name=%s, price=%d", name, price);
	}
}
