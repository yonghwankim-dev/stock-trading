package site.stocktrading.global.util.delay;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class DefaultDelayService implements DelayService {

	private final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public void delay(long timeout, ChronoUnit unit) {
		DelayService.super.delay(timeout, unit);
	}

	@Override
	public void delayRandomSecond(int start, int end) {
		DelayService.super.delayRandomSecond(start, end);
	}
}
