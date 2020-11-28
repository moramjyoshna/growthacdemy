package com.academy.growth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academy.growth.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{
	Optional<Student> findByEmailIdAndPassword(String emailId, String password);

}
