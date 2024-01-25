package com.cesar.Courses.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.cesar.Courses.persistence.Course;
import com.cesar.Courses.persistence.StudentDTO;
import com.cesar.Courses.service.Course_Service_Impl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(Controller.class)
public class ControllerTest {

	@MockBean
	private Course_Service_Impl service;

	@Autowired
	private MockMvc mvc;
	
	private Long id = 1L;
	
	ObjectMapper mapper = new ObjectMapper();	
	
	
	@Test
	public void createCourse_shouldReturnCreated() throws Exception {
		
		Course request = new Course();
		request.setName( "Something" );
		
		Course requestProceced = request;
		requestProceced.setId( id );
		
		when( service.create(any(Course.class)) ).thenReturn( requestProceced );
		
		mvc.perform( post("/courses/create")
				
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content( mapper.writeValueAsString( request ))
			)
		.andExpect( jsonPath("$.id", is(1) ))
		.andExpect( status().isCreated() );
		
		verify( service ).create( any(Course.class) );
	}
	
	@Test
	public void createdEmptyCourse_shouldReturnBadRequest() throws Exception {
		
		Course emptyRequest = new Course();
		
		mvc.perform( post("/courses/create")
				
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content( mapper.writeValueAsString( emptyRequest ))
			)

		.andExpect( status().isBadRequest() );
		
		verify( service, never() ).create( any(Course.class) );
	}
	
	
	@Test
	public void getStudentsInExistentedCourse_shouldReturnOk() throws Exception {
	
		Course existentedCourse = new Course();
		existentedCourse.setId( id );
		existentedCourse.setName( "Something" );
		
		StudentDTO student = new StudentDTO();
		student.setName("Csar");
		
		List<StudentDTO> students = new ArrayList<StudentDTO>();
		students.add(student);
		
		when( service.getById(id) ).thenReturn( Optional.of(existentedCourse) );
		when( service.getStudentsByCourse( id )).thenReturn( students );
		
		mvc.perform( get("/courses/" + id + "/getStudents")
				
				.contentType(MediaType.APPLICATION_JSON)
			)
		.andExpect( status().isOk() );
		
		verify( service ).getById( id );
		verify( service ).getStudentsByCourse( id );
	}
	
	
	
	
	@Test
	public void getStudentsInCourseWithNoStudents_shouldReturnEmptyStudentsListAndOk() throws Exception {
	
		Course existentedCourse = new Course();
		existentedCourse.setId( id );
		existentedCourse.setName( "Something" );
		
		List<StudentDTO> emptyStudentsList = new ArrayList<StudentDTO>();
		
		when( service.getById(id) ).thenReturn( Optional.of(existentedCourse) );
		when( service.getStudentsByCourse( id )).thenReturn( emptyStudentsList );
		
		mvc.perform( get("/courses/" + id + "/getStudents")
				
				.contentType(MediaType.APPLICATION_JSON)
			)
		.andExpect( jsonPath("$.students", is(emptyStudentsList) ))
		.andExpect( status().isOk() );
		
		verify( service ).getById( id );
		verify( service ).getStudentsByCourse( id );	
	}
	
	
	
	@Test
	public void getStudentsInNotExistentedCourse_shouldReturnNotFound() throws Exception {
		
		Long idNonExistentedCourse = 1L;
		
		when( service.getById(idNonExistentedCourse)).thenReturn( Optional.empty() );
		
		mvc.perform( get("/courses/" + idNonExistentedCourse + "/getStudents")
				.contentType(MediaType.APPLICATION_JSON))
		
		.andExpect( status().isNoContent() ); 
		
		verify( service ).getById( idNonExistentedCourse );
		verify( service, never() ).getStudentsByCourse( idNonExistentedCourse );	
	}
}
