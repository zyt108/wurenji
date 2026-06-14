package com.md.basePlatform;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.datasource.url=jdbc:sqlite::memory:",
		"spring.datasource.driver-class-name=org.sqlite.JDBC",
		"spring.datasource.druid.url=jdbc:sqlite::memory:",
		"spring.datasource.druid.driver-class-name=org.sqlite.JDBC"
})
class BasePlatformApplicationTests {

	@Test
	void contextLoads() {
	}

}
