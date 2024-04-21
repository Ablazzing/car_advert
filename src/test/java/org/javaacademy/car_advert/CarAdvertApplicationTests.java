package org.javaacademy.car_advert;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.javaacademy.car_advert.entity.Advert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
class CarAdvertApplicationTests {
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@SneakyThrows
	void contextLoads() {
		Advert advert = Advert.builder()
				.color("color")
				.brand("brand")
				.price(BigDecimal.TEN)
				.model("model")
				.createdDate(LocalDate.now())
				.build();
		String text = objectMapper.writeValueAsString(advert);
		System.out.println(text);
	}

}
