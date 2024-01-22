package com.cesar.Courses.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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
	
	private Long id = (long) 1;
	private Course course = new Course();
	private StudentDTO student = new StudentDTO();
	
	ObjectMapper mapper = new ObjectMapper();
	
	
	
	@BeforeEach
	void init() {
		
		course.setId( id );
		course.setName( "Something" );
		
		student.setName("Csar");
		
		when( service.create(any(Course.class)) ).thenReturn( course );
		when( service.getById(id) ).thenReturn( Optional.of(course) );
	
		List<StudentDTO> students = new ArrayList<StudentDTO>();
		students.add(student);
		
		when( service.getStudentsByCourse(id) ).thenReturn( students );
	}
	
	
	
	@Test
	public void create_Success() throws Exception {
		
		mvc.perform( post("/courses/create")
				
				.accept(MediaType.APPLICATION_JSON)
				
				.contentType(MediaType.APPLICATION_JSON)
				.content( mapper.writeValueAsString( course ))
			)
		.andExpect( status().isCreated() );
	}
	
	@Test
	public void create_NameMissed() throws Exception {
		
		Course badRequest = new Course();
		
		mvc.perform( post("/courses/create")
				
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content( mapper.writeValueAsString( badRequest ))
			)

		.andExpect( status().isBadRequest() );
	}
	
	
	@Test
	public void getStudentsInCourse_Success() throws Exception {
	
		mvc.perform( get("/courses/" + id + "/getStudents")
				
				.contentType(MediaType.APPLICATION_JSON)
			)

		.andExpect( status().isOk() );
	}
}
