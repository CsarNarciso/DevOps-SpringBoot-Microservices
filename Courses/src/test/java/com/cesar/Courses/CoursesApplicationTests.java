package com.cesar.Courses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.cesar.Courses.controller.Controller;
import com.cesar.Courses.feign.FeignStudent;
import com.cesar.Courses.persistence.Course;
import com.cesar.Courses.persistence.StudentDTO;
import com.cesar.Courses.repository.Course_Repository;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class CoursesApplicationTests {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate client;
	
	@Autowired
	private Controller controller;
	
	@Mock
	private Course_Repository repo;
	@Mock
	private FeignStudent feign;
	
	
	
	
	
	
	
	@Test
	void controllerIsNotNull() {
		
		assertThat( controller ).isNotNull();
	}
	
	
	
	@Test
	void createCourse_Success() {
		
		String name = "something";
		
		Course courseRequest = new Course();
		courseRequest.setName( name );
		
		Course courseResponse = new Course();
		courseResponse.setId(Long.valueOf(0));
		courseResponse.setName( name );		
		
		
		when( repo.save( any(Course.class))).thenReturn( courseResponse );
		
		courseRequest = repo.save( courseRequest );
		
		assertNotNull( courseRequest );
		assertEquals( courseRequest , courseResponse);
	}
	
	
	
	@Test
	void getStudentsInCourse_Success() {
		
		Long requestId = Long.valueOf(0);
		
		Course existingCourse = new Course();
		existingCourse.setId( requestId );		
		existingCourse.setName( "something" );
		
		StudentDTO Suscribededstudent = new StudentDTO();
		Suscribededstudent.setName( "somebody" );
		
		List<StudentDTO> foundStudents = new ArrayList<StudentDTO>();
		foundStudents.add( Suscribededstudent );		
		
		
		
		when( repo.findById( requestId )).thenReturn( Optional.of( existingCourse ));
		when( feign.getStudentsByCourse( requestId )).thenReturn( foundStudents );
		
		Optional<Course> courseResponse = repo.findById( requestId );
		List<StudentDTO> studentsResponse = feign.getStudentsByCourse( requestId);
		
		assertThat( courseResponse ).isNotNull();	
		assertThat( studentsResponse ).isNotNull();	
		assertEquals( courseResponse.get(), existingCourse );
		assertEquals( studentsResponse, foundStudents );
	}
}
