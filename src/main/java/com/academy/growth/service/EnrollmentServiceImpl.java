package com.academy.growth.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.academy.growth.dto.CancelEnrollmentResponseDTO;
import com.academy.growth.dto.EnrollmentRequestDto;
import com.academy.growth.dto.EnrollmentResponseDto;
import com.academy.growth.dto.EnrollmentsResponseDto;
import com.academy.growth.entity.Course;
import com.academy.growth.entity.Enrollment;
import com.academy.growth.entity.TrainingCalendar;
import com.academy.growth.exception.EnrollmentException;
import com.academy.growth.exception.EnrollmentIdNotFoundException;
import com.academy.growth.exception.StudentNotFoundException;
import com.academy.growth.repository.CourseRepository;
import com.academy.growth.repository.EnrollmentRepository;
import com.academy.growth.repository.TrainingCalendarRepository;
import com.academy.growth.util.GrowthAcademyConstants;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	TrainingCalendarRepository trainingCalendarRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	EnrollmentRepository enrollmentRepository;

	@Override
	public EnrollmentResponseDto enroll(EnrollmentRequestDto enrollmentRequestDto) throws EnrollmentException {

		// Get Training details based on training id
		Optional<TrainingCalendar> traingCalendarDetails = trainingCalendarRepository
				.findByTrainingId(enrollmentRequestDto.getTrainingId());

		if (!traingCalendarDetails.isPresent()) {
			logger.info(GrowthAcademyConstants.TRAINING_DOES_NOT_EXISTS);
			throw new EnrollmentException(GrowthAcademyConstants.TRAINING_DOES_NOT_EXISTS);
		}

		// Get Course details based on course code
		Optional<Course> courseDetails = courseRepository.findByCourseCode(traingCalendarDetails.get().getCourseCode());

		if (!courseDetails.isPresent()) {
			logger.info(GrowthAcademyConstants.COURSE_DOES_NOT_EXISTS);
			throw new EnrollmentException(GrowthAcademyConstants.COURSE_DOES_NOT_EXISTS);
		}

		LocalDateTime currentDate = LocalDateTime.now();
		LocalDateTime twoDaysAfterCurrentDate = currentDate.plusDays(2);
		LocalDateTime courseStartDate = traingCalendarDetails.get().getStartDate().toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDateTime();
		// LocalDateTime twentyDaysAfterCurrentDate = courseStartDate.minusDays(20);

		if (courseStartDate.isBefore(currentDate) || courseStartDate.isBefore(twoDaysAfterCurrentDate)) {
			logger.info(GrowthAcademyConstants.CANNOT_EROLL_TO_COURSE);
			throw new EnrollmentException(GrowthAcademyConstants.CANNOT_EROLL_TO_COURSE);
		}

		// Get the count of the no of scheduled courses for the student, if any
		Integer count = enrollmentRepository.getScheduledCoursesCount(enrollmentRequestDto.getStudentId(), "SCHEDULED");

		if (count >= 3) {
			logger.info(GrowthAcademyConstants.COURSE_ENROLLMENT_EXCEEDED);
			throw new EnrollmentException(GrowthAcademyConstants.COURSE_ENROLLMENT_EXCEEDED);
		}

		Enrollment enrollment = new Enrollment();
		enrollment.setCourseCode(traingCalendarDetails.get().getCourseCode());
		enrollment.setCourseName(courseDetails.get().getCourseName());
		enrollment.setEnrollmentStatus("SCHEDULED");
		enrollment.setStudentId(enrollmentRequestDto.getStudentId());
		enrollment.setTrainingId(enrollmentRequestDto.getTrainingId());

		Enrollment studentEnrollment = enrollmentRepository.save(enrollment);

		EnrollmentResponseDto enrollmentResponseDto = new EnrollmentResponseDto();
		enrollmentResponseDto.setEnrollmentId(studentEnrollment.getEnrollmentId());
		enrollmentResponseDto.setMessage("You have been enrolled to the course "
				+ traingCalendarDetails.get().getCourseCode() + " " + courseDetails.get().getCourseName());
		enrollmentResponseDto.setStatusCode(HttpStatus.CREATED.value());

		return enrollmentResponseDto;
	}

	@Override
	public Map<String, List<EnrollmentsResponseDto>> getListofEnrollments(Integer studentId)
			throws StudentNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<CancelEnrollmentResponseDTO> cancelEnrollment(Integer enrollmentId)
			throws EnrollmentIdNotFoundException {

		logger.info(GrowthAcademyConstants.CANCEL_ENROLLMENTT_SERVICE);
		Optional<CancelEnrollmentResponseDTO> enrollment = enrollmentRepository.findByEnrollmentId(enrollmentId);
		if (!enrollment.isPresent()) {
			throw new EnrollmentIdNotFoundException(GrowthAcademyConstants.ENROLLMENT_INFO_NOT_EXIST);
		}
		if (enrollment.get().getEnrollmentStatus().equals("CANCELLED")) {
			throw new EnrollmentIdNotFoundException(GrowthAcademyConstants.ENROLLEMENT_ALREADY_CANCELLED);
		}
		enrollment.get().setEnrollmentStatus("CANCELLED");
		return enrollment;
	}

}
