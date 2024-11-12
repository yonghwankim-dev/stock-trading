package site.stocktrading.api.stock.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import site.stocktrading.api.stock.domain.Stock;

@SpringBootTest
class MemoryStockRepositoryTest {

	@Autowired
	private StockRepository repository;

	@BeforeEach
	void setup(){
		repository.save(new Stock("samsung", 50000));
		repository.save(new Stock("kakao", 40000));
		repository.save(new Stock("sk hynix", 30000));
	}

	@DisplayName("종목 리스트를 조회한다")
	@Test
	void givenStocks_whenFindAll_thenReturnListOfStock(){
	    // given

	    // when
		List<Stock> actual = repository.findAll();
		// then
		Assertions.assertThat(actual).hasSize(3);
	}

}
