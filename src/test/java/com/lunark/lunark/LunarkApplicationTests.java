package com.lunark.lunark;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootTest
class LunarkApplicationTests {
	@Bean
	public Clock clock() {
		return Clock.systemDefaultZone();
	}

	@Test
	void contextLoads() {
	}

}
