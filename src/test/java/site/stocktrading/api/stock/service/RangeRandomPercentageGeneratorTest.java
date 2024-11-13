package site.stocktrading.api.stock.service;

import java.util.concurrent.ThreadLocalRandom;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class RangeRandomPercentageGeneratorTest {

	private ThreadLocalRandom random;
	private RandomPercentageGenerator generator;

	@BeforeEach
	void setUp() {
		random = Mockito.mock(ThreadLocalRandom.class);
		double range = 0.01;
		generator = new RangeRandomPercentageGenerator(random, range);
	}

	@DisplayName("범위가 주어지고 범위를 기준으로한 랜덤 값 생성시 -(range+1)% ~ +(range+1)%의 값을 생성한다")
	@Test
	void givenRange_whenGenerate_thenRandomPercentage() {
		// given
		BDDMockito.given(random.nextDouble()).willReturn(0.0);
		BDDMockito.given(random.nextBoolean()).willReturn(true);
		// when
		double actual = generator.generate();
		// then
		Assertions.assertThat(actual).isCloseTo(0.01, Percentage.withPercentage(0));
	}

}
