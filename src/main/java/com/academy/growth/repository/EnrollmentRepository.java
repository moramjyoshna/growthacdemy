package com.academy.growth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.academy.growth.dto.CancelEnrollmentResponseDTO;
import com.academy.growth.entity.Enrollment;

@Repository("studentEnrollmentRepository")
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer>{
	
	@Query("SELECT COUNT(*)FROM Enrollment e WHERE  e.studentId = :studentId AND e.enrollmentStatus = :enrollmentStatus")
	public Integer getScheduledCoursesCount(@Param("studentId") Integer studentId, @Param("enrollmentStatus") String enrollmentStatus);
	
	@Query("SELECT FROM Enrollment e WHERE  e.studentId = :studentId AND e.enrollmentStatus = :enrollmentStatus")
	public Optional<Enrollment> checkEnrollmentStatus(@Param("studentId") Integer studentId, @Param("enrollmentStatus") String enrollmentStatus);

	CancelEnrollmentResponseDTO findByEnrollmentId(Integer enrollmentId);
	
}
