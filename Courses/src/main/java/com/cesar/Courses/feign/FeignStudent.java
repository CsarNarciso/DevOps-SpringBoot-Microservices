package com.cesar.Courses.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cesar.Courses.persistence.StudentDTO;

@FeignClient(name = "CoursesClient", url = "http://localhost:8001", path = "/students")
public interface FeignStudent {

	@GetMapping("/getByCourse/{courseId}")
	List<StudentDTO> getStudentsByCourse(@PathVariable("courseId") Long courseId);
}
