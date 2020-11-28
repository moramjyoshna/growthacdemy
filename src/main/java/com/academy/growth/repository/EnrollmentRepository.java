package com.academy.growth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academy.growth.dto.CancelEnrollmentResponseDTO;
import com.academy.growth.entity.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

	CancelEnrollmentResponseDTO findByEnrollmentId(Integer enrollmentId);
}
