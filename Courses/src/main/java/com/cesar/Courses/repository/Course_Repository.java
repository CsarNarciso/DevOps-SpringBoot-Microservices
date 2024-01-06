package com.cesar.Courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cesar.Courses.persistence.Course;

@EnableJpaRepositories
public interface Course_Repository extends JpaRepository<Course, Long>{
}
