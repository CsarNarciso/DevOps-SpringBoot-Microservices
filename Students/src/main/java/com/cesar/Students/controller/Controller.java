package com.cesar.Students.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cesar.Students.Persistence.Student;
import com.cesar.Students.Persistence.StudentDTO;
import com.cesar.Students.repository.Student_Repository;

public class Controller {

	@PostMapping("/create")
	private ResponseEntity<?> createStudent(@RequestBody Student student) {
		
		//save student in BBDD
		student = repo.save( student );
		
		return ResponseEntity.status(HttpStatus.CREATED).body( student );
	}
	
	
	@GetMapping("/getByCourse/{courseId}")
	private List<StudentDTO> getStudentsByCourse(@PathVariable("courseId") Long courseId) {
		
		//find students belong to id course
		List<Student> students = repo.findAllByActualCourseId(courseId);
		
		//caste them to studentDTO
		if ( students != null ) {
						
			List<StudentDTO> studentsDTO = students.stream()
				.map( s -> mapper.map(s, StudentDTO.class) )
				.collect(Collectors.toList());		
			
			return studentsDTO;
		}
		
		return null;
	}
	
	
	
	@Autowired
	private Student_Repository repo;
	
	@Autowired
	private ModelMapper mapper;
	
}
