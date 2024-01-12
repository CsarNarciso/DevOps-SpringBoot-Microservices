package com.cesar.Courses.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cesar.Courses.persistence.Course;
import com.cesar.Courses.repository.Course_Repository;

@Service
public class Course_Service {

	public Course create(Course course) {
		
		return repo.save( course );
	}
	
	
	public Optional<Course> getById(Long courseId) {
		
		return repo.findById( courseId );
	}
	
	
	@Autowired
	private Course_Repository repo;
}
