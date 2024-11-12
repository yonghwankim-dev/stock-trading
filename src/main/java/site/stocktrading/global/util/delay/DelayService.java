package site.stocktrading.global.util.delay;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public interface DelayService {

	default void delay(long timeout, ChronoUnit unit) {
		try {
			TimeUnit.of(unit).sleep(timeout);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	default void delayRandomSecond(int start, int end) {
		long timeout = ThreadLocalRandom.current().nextLong(start, end + 1L);
		delay(timeout, ChronoUnit.SECONDS);
	}
}
