package com.cesar.Courses.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cesar.Courses.feign.FeignStudent;
import com.cesar.Courses.persistence.Course;
import com.cesar.Courses.persistence.StudentDTO;
import com.cesar.Courses.repository.Course_Repository;

@Service
public class Course_Service_Impl implements Course_Service {

	
	public Course create(Course course) {
		
		return repo.save( course );
	}
	
	
	public Optional<Course> getById(Long courseId) {
		
		return repo.findById( courseId );
	}
	
	public List<StudentDTO> getStudentsByCourse(Long courseId) {
		
		return client.getStudentsByCourse(courseId);
	}


	@Autowired
	private Course_Repository repo;
	@Autowired
	private FeignStudent client;
}
