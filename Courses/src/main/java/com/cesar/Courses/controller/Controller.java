package com.cesar.Courses.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cesar.Courses.feign.FeignStudent;
import com.cesar.Courses.persistence.Course;
import com.cesar.Courses.persistence.StudentDTO;
import com.cesar.Courses.repository.Course_Repository;

@RestController
@RequestMapping("/courses")
public class Controller {

	
	@PostMapping("/create")
	private ResponseEntity<?> create(@RequestBody Course course) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body( repo.save( course ));
	}
	
	
	@GetMapping("/{courseId}/getStudents")
	private ResponseEntity<?> getStudentsInCourse(@PathVariable("courseId") Long courseId) {
		
		Map<String, Object> response = new HashMap<>();
		Optional<Course> courseOpt = repo.findById( courseId );
		
		
		if ( courseOpt.isPresent() ) {
			
			Course course = courseOpt.get();
			
			List<StudentDTO> students = client.getStudentsByCourse( courseId );
			
			if ( students != null && ! students.isEmpty() ) {
				
				response.put( "courseName", course.getName() );
				response.put("students", students);
				
				return ResponseEntity.ok( response );
			}
		}
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	
	
	@Autowired
	private Course_Repository repo;
	
	@Autowired
	private FeignStudent client;
}
