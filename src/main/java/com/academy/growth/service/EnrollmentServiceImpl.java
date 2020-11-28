package com.academy.growth.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.academy.growth.dto.EnrollmentRequestDto;
import com.academy.growth.dto.EnrollmentResponseDto;
import com.academy.growth.dto.EnrollmentsResponseDto;
import com.academy.growth.entity.Course;
import com.academy.growth.entity.Enrollment;
import com.academy.growth.entity.Student;
import com.academy.growth.entity.TrainingCalendar;
import com.academy.growth.exception.EnrollmentException;
import com.academy.growth.exception.StudentNotFoundException;
import com.academy.growth.repository.CourseRepository;
import com.academy.growth.repository.EnrollmentRepository;
import com.academy.growth.repository.StudentRepository;
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
		
	@Autowired
	StudentRepository studentRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);

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

	/**
	 * This method views the student's enrollment history grouped by the enrollment status
	 * 
	 * @author swathi
	 * @param studentId contains studentId
	 * 
	 * @throws StudentNotFoundException thrown when student is not found
	 * @return EnrollmentsResponseDto contains enrollment history grouped by the enrollment status.
	 */
	@Override
	public Map<String, List<EnrollmentsResponseDto>> getListofEnrollments(Integer studentId) throws StudentNotFoundException{

		logger.info("To display List of Enrollments");

		List<EnrollmentsResponseDto> responseList = new ArrayList<>();

		List<Object> response = studentEnrollmentRepository.getListofEnrollmentsGroupedByStatus(studentId);

		if (!response.isEmpty()) {
			for (int iLoop = 0; iLoop < response.size(); iLoop++) {

				Object[] obj = (Object[]) response.get(iLoop);

				EnrollmentsResponseDto responseDto = new EnrollmentsResponseDto();
				responseDto.setCourseName(obj[0].toString());
				responseDto.setStudentId(Integer.valueOf(obj[1].toString()));
				responseDto.setEnrollmentStatus(obj[2].toString());
				responseDto.setTrainingId(Integer.valueOf(obj[3].toString()));
				responseDto.setCourseCode(obj[4].toString());

				responseList.add(responseDto);
			}
		}

		Optional<Student> student = studentRepository.findById(studentId);
		if (!student.isPresent()) {
			throw new StudentNotFoundException(GrowthAcademyConstants.STUDENT_NOT_FOUND);
		} else {

			Map<String, List<EnrollmentsResponseDto>> enrollmentResponse = responseList.stream()
					.collect(Collectors.groupingBy(EnrollmentsResponseDto::getEnrollmentStatus));

			return enrollmentResponse;
		}
	}
}
