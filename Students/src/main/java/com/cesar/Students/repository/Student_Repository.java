package com.cesar.Students.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cesar.Students.Persistence.Student;

@EnableJpaRepositories
public interface Student_Repository extends JpaRepository<Student, Long>{
	
	public List<Student> findAllByActualCourseId(Long id);
}
