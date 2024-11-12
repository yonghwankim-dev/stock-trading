package site.stocktrading.global.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Profile(value = {"!test"})
@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
	private boolean alreadySetup = false;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}
		alreadySetup = true;
	}
}
