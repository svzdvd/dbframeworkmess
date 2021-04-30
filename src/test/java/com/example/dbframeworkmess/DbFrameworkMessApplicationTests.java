package com.example.dbframeworkmess;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DbFrameworkMessApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void testCreateUser1() {
		UserEntity userEntity = userService.createUser1("Paolino", "Paperino");
		assertEquals("Paolino", userEntity.getFirstName());
	}

	@Test
	void testCreateUser2() {
		UserEntity userEntity = userService.createUser2("Paolino", "Paperino");
		assertEquals("Paolino", userEntity.getFirstName());
	}

	@Test
	void testCreateUser3() {
		UserEntity userEntity = userService.createUser3("Paolino", "Paperino");
		assertEquals("Paolino", userEntity.getFirstName());
	}

	@Test
	void testCreateUser4() {
		UserEntity userEntity = userService.createUser4("Paolino", "Paperino");
		assertEquals("Paolino", userEntity.getFirstName());
	}
}
