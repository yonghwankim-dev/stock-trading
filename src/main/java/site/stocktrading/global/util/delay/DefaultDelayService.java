package site.stocktrading.global.util.delay;

import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

@Service
public class DefaultDelayService implements DelayService {
	@Override
	public void delay(long timeout, ChronoUnit unit) {
		DelayService.super.delay(timeout, unit);
	}
}
