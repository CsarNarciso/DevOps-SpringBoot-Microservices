package com.cesar.Courses.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cesar.Courses.persistence.Course;

@SpringBootTest
public class Course_RepositoryTest {

	@Mock
	private Course_Repository repo;
	
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
	void save() {

		when( repo.save( any( Course.class ))).thenReturn( course );
		
		Course response = repo.save( course );
		
		assertThat( response ).isNotNull();
		assertEquals( response, course );
	}
	
	@Test
	void findById() {
		
		when( repo.findById(id)).thenReturn( Optional.of( course ));
		
		Course foundCourse = repo.findById(id).get();
		
		assertThat( foundCourse ).isNotNull();
		assertEquals( foundCourse, course );
	}
}
