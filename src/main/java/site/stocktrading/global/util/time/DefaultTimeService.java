package site.stocktrading.global.util.time;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class DefaultTimeService implements TimeService {
	@Override
	public LocalDateTime now() {
		return TimeService.super.now();
	}
}
