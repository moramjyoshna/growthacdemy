package com.academy.growth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academy.growth.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{

}
