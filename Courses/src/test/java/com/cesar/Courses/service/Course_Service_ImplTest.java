 package com.cesar.Courses.service;

 import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cesar.Courses.feign.FeignStudent;
import com.cesar.Courses.persistence.Course;
import com.cesar.Courses.persistence.StudentDTO;
import com.cesar.Courses.repository.Course_Repository;

public class Course_Service_ImplTest {

	@Mock
	private Course_Repository repo;
	
	@Mock
	private FeignStudent client;
	
	@InjectMocks
	private Course_Service_Impl service;
	
	private Long id = 1L;
	private Course course = new Course();
	
	
	@BeforeEach
	void init() {
		
		MockitoAnnotations.openMocks(this);
		
		course.setId( id );
		course.setName( "Something" );
	}
	
	
	
	@Test
	void create() {
	
		Course request = course;
		request.setId(null);
		
		when( repo.save( any(Course.class))).thenReturn( course );
		
		Course response = service.create( course );
		
		assertThat( response ).isNotNull();
		assertEquals( response, course );
		
		verify( repo ).save( any(Course.class ));
	}
	
	
	@Test
	void getById() {
		
		when( repo.findById(id)).thenReturn( Optional.of( course ));
		
		Optional<Course> optionalFoundCourse = service.getById(id);
		Course foundCourse = optionalFoundCourse.get();
		
		assertThat( foundCourse ).isNotNull();
		assertEquals( foundCourse.getId(), course.getId() );
		assertEquals( foundCourse.getName(), course.getName() );
		
		verify( repo ).findById(id);
	}
	
	
	@Test
	void getStudentsByCourse() {
		
		StudentDTO student = new StudentDTO();
		student.setName("Csar");
		
		List<StudentDTO> students = new ArrayList<StudentDTO>();
		students.add(student);
		
		when( client.getStudentsByCourse(id)).thenReturn( students );
		
		List<StudentDTO> response = service.getStudentsByCourse(id);
		
		assertThat( response ).isNotEmpty();
		
		verify( client ).getStudentsByCourse(id);
		verify( repo, never() ).findById(id);
	}
}
