package site.stocktrading.api.stock.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Stock {
	private final String name;
	private final int price;
}
