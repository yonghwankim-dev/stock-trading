package site.stocktrading.api.stock.domain;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class Stock {
	private final String name;
	private final int price;

	public Stock saveTo(Map<String, Stock> store) {
		return store.put(name, this);
	}
}
