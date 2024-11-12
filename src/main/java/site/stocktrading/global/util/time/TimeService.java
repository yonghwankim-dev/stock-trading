package site.stocktrading.global.util.time;

import java.time.LocalDateTime;

public interface TimeService {
	default LocalDateTime now() {
		return LocalDateTime.now();
	}
}
