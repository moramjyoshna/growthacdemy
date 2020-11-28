package com.academy.growth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academy.growth.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>{
	
	public Optional<Course> findByCourseCode(String courseCode);
	
	

}
