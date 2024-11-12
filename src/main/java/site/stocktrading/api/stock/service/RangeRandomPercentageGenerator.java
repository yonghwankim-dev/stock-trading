package site.stocktrading.api.stock.service;

import java.util.Random;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * 특정 값을 기준으로 랜덤한 범위 값을 생성하는 생성기
 */
@RequiredArgsConstructor
public class RangeRandomPercentageGenerator implements RandomPercentageGenerator {

	private final Random random = new Random();
	private final double range;

	/**
	 * range를 기준으로 -(range+1)% ~ +(range+1) 사이의 랜덤 값을 생성하여 반환
	 * 예를 들어 range=0.01이라면 랜덤으로 생성되는 값의 범위는 -0.02 ~ +0.02 이다
	 * @return -(range+1)% ~ +(range+1) 사이의 랜덤 값
	 */
	@Override
	public double generate() {
		double percentage = range + (range * random.nextDouble()); // range ~ (range+1)
		return random.nextBoolean() ? percentage : -percentage;
	}
}
