package com.cesar.Courses.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.cesar.Courses.persistence.Course;

public class Course_ServiceTest {

	@Mock
	private Course_Service service;
	
	private Long id;
	private Course course;
	
	
	@BeforeEach
	void init() {
		
		id = (long) 1;
		
		course = new Course();
		course.setId( id );
		course.setName( "Something" );
	}
	
	
	
	@Test
	void create() {
	
		when( service.create( any( Course.class ))).thenReturn( course );
		
		Course response = service.create( course );
		
		assertThat( response ).isNotNull();
		assertEquals( response, course );
	}
	
	@Test
	void getById() {
		
		when( service.getById(id)).thenReturn( Optional.of( course ));
		
		Course foundCourse = service.getById(id).get();
		
		assertThat( foundCourse ).isNotNull();
		assertEquals( foundCourse, course );
	}
}
