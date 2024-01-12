package com.cesar.Courses.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cesar.Courses.persistence.Course;
import com.cesar.Courses.repository.Course_Repository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ControllerTest {

//	@LocalServerPort
//	int port;
	
	@Autowired
	private TestRestTemplate client;
	
//	@Autowired
//	private FeignStudent feign;
	
	@Mock
	private Course_Repository repo;
	
	
	private Long id = (long) 1;
	private String url = "http://localhost:" + 8080 + "/courses";
	private Course course;
	
	
	@BeforeEach
	void init() {
		
		course = new Course();
		course.setId( id );
		course.setName( "Something" );
	}
	
	
	
	
	@Test
	void createCourse_Success() {
				
		Course request = new Course();
		request.setName( "Something" );
		
		when( repo.save( any(Course.class))).thenReturn( request );
		
		ResponseEntity response = client.postForEntity(url + "/create", request, Object.class);
		
		System.out.println("-----------------" + response.getClass());
		
		assertNotNull( response );
		assertEquals( response.getStatusCode() , HttpStatus.CREATED );
	}
	
	
	
//	@Test
//	void getStudentsInCourse_Success() {
//		
//		StudentDTO Suscribededstudent = new StudentDTO();
//		Suscribededstudent.setName( "somebody" );
//		
//		List<StudentDTO> foundStudents = new ArrayList<StudentDTO>();
//		foundStudents.add( Suscribededstudent );		
//		
//		
//		when( repo.findById( id )).thenReturn( Optional.of( course ));
//		when( feign.getStudentsByCourse( id )).thenReturn( foundStudents );
//		
//		
//		client.getForObject(url + "/getStudents", Map.class);
//		
//		assertThat( courseResponse ).isNotNull();	
//		assertThat( studentsResponse ).isNotNull();	
//		assertEquals( courseResponse.get(), existingCourse );
//		assertEquals( studentsResponse, foundStudents );
//	}
	
}
