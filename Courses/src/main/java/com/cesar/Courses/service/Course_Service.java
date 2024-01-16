package com.cesar.Courses.service;

import java.util.Optional;

import com.cesar.Courses.persistence.Course;

public interface Course_Service {

	public Course create(Course course);
		
	public Optional<Course> getById(Long courseId);
}
