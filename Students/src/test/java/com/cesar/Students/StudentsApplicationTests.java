package com.cesar.Students;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cesar.Students.controller.Controller;

@SpringBootTest
class StudentsApplicationTests {

	@Autowired
	@Mock private Controller controller;
		
	@Test
	void controllerNotNull() throws Exception {
		
		assertThat( controller ).isNotNull();
	}
	
}
