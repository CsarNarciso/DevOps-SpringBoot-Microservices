 package com.cesar.Courses.service;

 import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cesar.Courses.persistence.Course;
import com.cesar.Courses.repository.Course_Repository;

public class Course_Service_ImplTest {

	private Course_Repository repo = mock(Course_Repository.class);
	
	private Course_Service_Impl service = new Course_Service_Impl(repo);
	
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
	void create_success() {
	
		when( repo.save( any(Course.class))).thenReturn( course );
		
		Course response = service.create( course );
		
		assertThat( response ).isNotNull();
		assertEquals( response, course );
	}
	
	
	@Test
	void getById_success() {
		
		when( repo.findById(id)).thenReturn( Optional.of( course ));
		
		Optional<Course> foundCourse = service.getById(id);
		
		assertThat( foundCourse ).isNotEmpty();
		assertThat( foundCourse.get() ).isNotNull();
		assertEquals( foundCourse.get(), course );
	}
	
	
	@Test
	void getById_notFound() {
		
		when( repo.findById(id)).thenReturn( Optional.empty() );
		
		Optional<Course> notFoundCourse = service.getById(id);
		
		assertThat( notFoundCourse ).isEmpty();
	}
}
