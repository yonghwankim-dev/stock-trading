package site.stocktrading.api.stock.domain;

import java.util.Map;

import org.apache.logging.log4j.util.Strings;

import lombok.EqualsAndHashCode;
import site.stocktrading.api.stock.service.RandomPercentageGenerator;
import site.stocktrading.api.stock.service.RandomPriceGenerator;

@EqualsAndHashCode
public class Stock {
	private final String name;
	private final int price;

	public Stock(String name, int price) {
		this.name = name;
		this.price = price;

		if (Strings.isBlank(name)) {
			throw new IllegalArgumentException("Name cannot be blank");
		}
		if (this.price < 0) {
			throw new IllegalArgumentException("Price cannot be negative");
		}
	}

	public static Stock zero(String name) {
		return new Stock(name, 0);
	}

	public Stock newStock(RandomPercentageGenerator generator) {
		double percentageChange = generator.generate();
		int newPrice = (int)(price * (1 + percentageChange));
		return new Stock(name, newPrice);
	}

	public Stock newStock(RandomPriceGenerator generator) {
		int newPrice = generator.generate();
		return new Stock(name, newPrice);
	}

	public Stock saveTo(Map<String, Stock> store) {
		return store.put(name, this);
	}

	@Override
	public String toString() {
		return String.format("name=%s, price=%d", name, price);
	}
}
