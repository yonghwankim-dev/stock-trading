package site.stocktrading.api.stock.service;

import java.util.concurrent.ThreadLocalRandom;

public class DefaultRandomPriceGenerator implements RandomPriceGenerator {

	private final ThreadLocalRandom random;
	private final int start;
	private final int end;

	public DefaultRandomPriceGenerator(ThreadLocalRandom random, int start, int end) {
		this.random = random;
		this.start = start;
		this.end = end;
	}

	@Override
	public int generate() {
		return random.nextInt(start, end + 1);
	}
}
