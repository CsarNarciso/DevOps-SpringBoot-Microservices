package com.cesar.Courses.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cesar.Courses.persistence.Course;
import com.cesar.Courses.repository.Course_Repository;

@Service
public class Course_Service_Impl implements Course_Service {

	
	
	public Course_Service_Impl(Course_Repository repo) {
		super();
		this.repo = repo;
	}
	
	
	
	
	public Course create(Course course) {
		
		return repo.save( course );
	}
	
	
	public Optional<Course> getById(Long courseId) {
		
		return repo.findById( courseId );
	}
	
	


	private Course_Repository repo;
}
