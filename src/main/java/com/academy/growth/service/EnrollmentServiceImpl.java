package com.academy.growth.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

import com.academy.growth.dto.CancelEnrollmentResponseDTO;
import com.academy.growth.dto.EnrollmentRequestDto;
import com.academy.growth.dto.EnrollmentResponseDto;
import com.academy.growth.dto.EnrollmentsResponseDto;
import com.academy.growth.dto.UpdateEnrollmentRequestDto;
import com.academy.growth.dto.UpdateEnrollmentResponseDto;
import com.academy.growth.entity.Course;
import com.academy.growth.entity.Enrollment;
import com.academy.growth.entity.Student;
import com.academy.growth.entity.TrainingCalendar;
import com.academy.growth.exception.DuplicateEnrollmentException;
import com.academy.growth.exception.EnrollmentException;
import com.academy.growth.exception.EnrollmentIdNotFoundException;
import com.academy.growth.exception.InvalidEnrollmentIdException;
import com.academy.growth.exception.StudentNotFoundException;
import com.academy.growth.repository.CourseRepository;
import com.academy.growth.repository.EnrollmentRepository;
import com.academy.growth.repository.StudentRepository;
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

	@Autowired
	StudentRepository studentRepository;

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

		if (courseStartDate.isBefore(currentDate) || courseStartDate.isBefore(twoDaysAfterCurrentDate)) {
			logger.info(GrowthAcademyConstants.CANNOT_EROLL_TO_COURSE);
			throw new EnrollmentException(GrowthAcademyConstants.CANNOT_EROLL_TO_COURSE);
		}

		// Get the count of the no of scheduled courses for the student, if any
		Integer count = enrollmentRepository.getScheduledCoursesCount(enrollmentRequestDto.getStudentId(),
				GrowthAcademyConstants.SCHEDULED);

		if (count >= 3) {
			logger.info(GrowthAcademyConstants.COURSE_ENROLLMENT_EXCEEDED);
			throw new EnrollmentException(GrowthAcademyConstants.COURSE_ENROLLMENT_EXCEEDED);
		}

		Optional<Enrollment> enrollmentStatus = enrollmentRepository.checkEnrollmentStatus(
				enrollmentRequestDto.getStudentId(), enrollmentRequestDto.getTrainingId(),
				GrowthAcademyConstants.SCHEDULED, GrowthAcademyConstants.IN_PROGRESS);

		if (enrollmentStatus.isPresent()) {
			throw new EnrollmentException(GrowthAcademyConstants.COURSE_ALREADY_ENROLLED);
		}
		Enrollment enrollment = new Enrollment();
		enrollment.setCourseCode(traingCalendarDetails.get().getCourseCode());
		enrollment.setCourseName(courseDetails.get().getCourseName());
		enrollment.setEnrollmentStatus(GrowthAcademyConstants.SCHEDULED);
		enrollment.setStudentId(enrollmentRequestDto.getStudentId());
		enrollment.setTrainingId(enrollmentRequestDto.getTrainingId());

		Enrollment studentEnrollment = enrollmentRepository.save(enrollment);
		logger.info(GrowthAcademyConstants.ENROLL_SUCCESS);

		EnrollmentResponseDto enrollmentResponseDto = new EnrollmentResponseDto();
		enrollmentResponseDto.setEnrollmentId(studentEnrollment.getEnrollmentId());
		enrollmentResponseDto.setMessage("You have been enrolled to the course "
				+ traingCalendarDetails.get().getCourseCode() + " " + courseDetails.get().getCourseName());
		enrollmentResponseDto.setStatusCode(HttpStatus.CREATED.value());

		return enrollmentResponseDto;
	}

	/**
	 * This method views the student's enrollment history grouped by the enrollment
	 * status
	 * 
	 * @author swathi
	 * @param studentId contains studentId
	 * 
	 * @throws StudentNotFoundException thrown when student is not found
	 * @return EnrollmentsResponseDto contains enrollment history grouped by the
	 *         enrollment status.
	 */
	@Override
	public Map<String, List<EnrollmentsResponseDto>> getListofEnrollments(Integer studentId)
			throws StudentNotFoundException {

		logger.info("To display List of Enrollments");

		List<EnrollmentsResponseDto> responseList = new ArrayList<>();

		List<Object> response = enrollmentRepository.getListofEnrollmentsGroupedByStatus(studentId);

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

			return responseList.stream().collect(Collectors.groupingBy(EnrollmentsResponseDto::getEnrollmentStatus));
		}
	}

	@Override
	public UpdateEnrollmentResponseDto updateEnrollment(UpdateEnrollmentRequestDto updateEnrollmentRequestDto)
			throws InvalidEnrollmentIdException, DuplicateEnrollmentException {
		UpdateEnrollmentResponseDto updateEnrollmentResponseDto = new UpdateEnrollmentResponseDto();

		Optional<Enrollment> enrolledRecord = enrollmentRepository
				.findByEnrollmentId(updateEnrollmentRequestDto.getEnrollmentId());
		if (enrolledRecord.isPresent()) {
			if (updateEnrollmentRequestDto.getTrainingId() != enrolledRecord.get().getTrainingId()) {
				enrolledRecord.get().setTrainingId(updateEnrollmentRequestDto.getTrainingId());
				enrollmentRepository.save(enrolledRecord.get());
			} else {
				throw new DuplicateEnrollmentException(GrowthAcademyConstants.ALREADY_ENROLLED_EXCEPTION);
			}
		} else {
			throw new InvalidEnrollmentIdException(GrowthAcademyConstants.NO_ENROLLMENT_ID_EXCEPTION);
		}
		updateEnrollmentResponseDto.setMessage("Success");
		updateEnrollmentResponseDto.setStatusCode("200");

		return updateEnrollmentResponseDto;
	}

	@Override
	public Optional<CancelEnrollmentResponseDTO> cancelEnrollment(Integer enrollmentId)
			throws EnrollmentIdNotFoundException {

		logger.info(GrowthAcademyConstants.CANCEL_ENROLLMENTT_SERVICE);
		Optional<Enrollment> enrollment = enrollmentRepository.findByEnrollmentId(enrollmentId);
		if (!enrollment.isPresent()) {
			throw new EnrollmentIdNotFoundException(GrowthAcademyConstants.ENROLLMENT_INFO_NOT_EXIST);
		}
		if (enrollment.get().getEnrollmentStatus().equals("CANCELLED")) {
			throw new EnrollmentIdNotFoundException(GrowthAcademyConstants.ENROLLEMENT_ALREADY_CANCELLED);
		}
		enrollment.get().setEnrollmentStatus("CANCELLED");
		enrollmentRepository.save(enrollment.get());
		CancelEnrollmentResponseDTO cancelEnrollmentResponseDTO = new CancelEnrollmentResponseDTO();
		cancelEnrollmentResponseDTO.setCourseName(enrollment.get().getCourseName());
		cancelEnrollmentResponseDTO.setEnrollmentStatus(enrollment.get().getEnrollmentStatus());
		cancelEnrollmentResponseDTO.setMessage("Success");
		cancelEnrollmentResponseDTO.setStatusCode(200);
		return Optional.of(cancelEnrollmentResponseDTO);
	}

}
