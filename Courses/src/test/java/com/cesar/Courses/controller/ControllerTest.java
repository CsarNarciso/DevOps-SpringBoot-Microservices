package com.cesar.Courses.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cesar.Courses.persistence.Course;
import com.cesar.Courses.repository.Course_Repository;
import com.cesar.Courses.service.Course_Service_Impl;

public class ControllerTest {
	
	private Course_Repository repo = mock(Course_Repository.class);
	
	private Course_Service_Impl service = new Course_Service_Impl(repo);
	
	private Controller controller = new Controller(service);
	
	private Long id = (long) 1;
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
		
		when( repo.save( any(Course.class))).thenReturn( course );
	
		ResponseEntity<?> response = controller.create( request );
		
		assertNotNull( response );
		assertThat( response ).isExactlyInstanceOf( ResponseEntity.class );
		assertEquals( response.getBody(), course );
		assertEquals( response.getStatusCode() , HttpStatus.CREATED );
	}
}
