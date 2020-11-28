package com.academy.growth.repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.academy.growth.dto.CancelEnrollmentResponseDTO;
import com.academy.growth.entity.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer>{
	
	@Query("SELECT COUNT(*)FROM Enrollment e WHERE  e.studentId = :studentId AND e.enrollmentStatus = :enrollmentStatus")
	public Integer getScheduledCoursesCount(@Param("studentId") Integer studentId, @Param("enrollmentStatus") String enrollmentStatus);
	
	@Query("SELECT e FROM Enrollment e WHERE  e.studentId = :studentId AND e.trainingId = :trainingId AND (e.enrollmentStatus = :scheduledEnrollmentStatus OR e.enrollmentStatus = :progressEnrollmentStatus) ")
	public Optional<Enrollment> checkEnrollmentStatus(@Param("studentId") Integer studentId, @Param("trainingId") Integer trainingId, @Param("scheduledEnrollmentStatus") String scheduledEnrollmentStatus,@Param("progressEnrollmentStatus") String progressEnrollmentStatus );

	CancelEnrollmentResponseDTO findByEnrollmentId(Integer enrollmentId);
	
	@Query("select e.courseName,e.studentId,e.enrollmentStatus,e.trainingId,e.courseCode from Enrollment e where e.studentId = :studentId ")
	public List<Object> getListofEnrollmentsGroupedByStatus( @Param("studentId") Integer studentId);
	
}
