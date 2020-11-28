package com.academy.growth.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.academy.growth.dto.EnrollmentRequestDto;
import com.academy.growth.dto.EnrollmentResponseDto;
import com.academy.growth.entity.Course;
import com.academy.growth.entity.Enrollment;
import com.academy.growth.entity.TrainingCalendar;
import com.academy.growth.exception.EnrollmentException;
import com.academy.growth.repository.CourseRepository;
import com.academy.growth.repository.EnrollmentRepository;
import com.academy.growth.repository.TrainingCalendarRepository;
import com.academy.growth.util.GrowthAcademyConstants;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

	@Autowired
	TrainingCalendarRepository trainingCalendarRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	EnrollmentRepository studentEnrollmentRepository;

	@Override
	public EnrollmentResponseDto enroll(EnrollmentRequestDto enrollmentRequestDto) throws EnrollmentException {

		Optional<TrainingCalendar> traingCalendarDetails = trainingCalendarRepository
				.findByTrainingId(enrollmentRequestDto.getTrainingId());

		Integer count = studentEnrollmentRepository.getScheduledCount(enrollmentRequestDto.getStudentId(), "SCHEDULED");

		System.out.println("Count is :" + count);

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime currentDate = LocalDateTime.now();
		LocalDateTime twoDaysBefore = currentDate.minusDays(2);
		LocalDateTime twentyDaysAfter = currentDate.plusDays(20);
		LocalDateTime courseStartDate = traingCalendarDetails.get().getStartDate().toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDateTime();

		if (count >= 3) {
			throw new EnrollmentException(GrowthAcademyConstants.COURSE_ENROLLMENT_EXCEEDED);
		}

		if (!traingCalendarDetails.isPresent()) {
			throw new EnrollmentException(GrowthAcademyConstants.TRAINING_DOES_NOT_EXISTS);
		}

		Optional<Course> courseDetails = courseRepository.findByCourseCode(traingCalendarDetails.get().getCourseCode());

		if (!courseDetails.isPresent()) {
			throw new EnrollmentException(GrowthAcademyConstants.COURSE_DOES_NOT_EXISTS);
		}

		if (courseStartDate.isBefore(currentDate) || courseStartDate.isAfter(twoDaysBefore)
				|| courseStartDate.isAfter(twentyDaysAfter)) {
			throw new EnrollmentException(GrowthAcademyConstants.CANNOT_EROLL_TO_COURSE);
		}
		
		Enrollment enrollment = new Enrollment();
		enrollment.setCourseCode(traingCalendarDetails.get().getCourseCode());
		enrollment.setCourseName(courseDetails.get().getCourseName());
		enrollment.setEnrollmentStatus("SCHEDULED");
		enrollment.setStudentId(enrollmentRequestDto.getStudentId());
		enrollment.setTrainingId(enrollmentRequestDto.getTrainingId());

		Enrollment studentEnrollment = studentEnrollmentRepository.save(enrollment);

		EnrollmentResponseDto enrollmentResponseDto = new EnrollmentResponseDto();
		enrollmentResponseDto.setEnrollmentId(studentEnrollment.getEnrollmentId());
		enrollmentResponseDto.setMessage("You have been enrolled to the course "
				+ traingCalendarDetails.get().getCourseCode() + " " + courseDetails.get().getCourseName());
		enrollmentResponseDto.setStatusCode(HttpStatus.CREATED.value());

		return enrollmentResponseDto;
	}

}
