package com.academy.growth.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.academy.growth.dto.CancelEnrollmentResponseDTO;
import com.academy.growth.dto.EnrollmentRequestDto;
import com.academy.growth.dto.EnrollmentResponseDto;
import com.academy.growth.dto.EnrollmentsResponseDto;
import com.academy.growth.dto.UpdateEnrollmentRequestDto;
import com.academy.growth.dto.UpdateEnrollmentResponseDto;
import com.academy.growth.exception.DuplicateEnrollmentException;
import com.academy.growth.exception.EnrollmentException;
import com.academy.growth.exception.EnrollmentIdNotFoundException;
import com.academy.growth.exception.InvalidEnrollmentIdException;
import com.academy.growth.exception.StudentNotFoundException;
import com.academy.growth.service.EnrollmentService;
import com.academy.growth.util.GrowthAcademyConstants;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EnrollmentController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	EnrollmentService enrollmentService;

	@PostMapping("/enroll-course")
	public ResponseEntity<EnrollmentResponseDto> courseEnrollment(
			@RequestBody EnrollmentRequestDto enrollmentRequestDto) throws EnrollmentException {

		logger.info(GrowthAcademyConstants.ENROLLMENT_CONTROLLER);
		EnrollmentResponseDto enrollmentResponseDto = enrollmentService.enroll(enrollmentRequestDto);
		return new ResponseEntity<>(enrollmentResponseDto, HttpStatus.CREATED);
	}

	/**
	 * This method views the student's enrollment history grouped by the enrollment
	 * status
	 * 
	 * @author swathi
	 * @param studentId contains studentId
	 * 
	 * @throws StudentNotFoundException thrown when student not found
	 * @return EnrollmentsResponseDto contains enrollment history grouped by the
	 *         enrollment status.
	 */
	@GetMapping("/student/{studentId}/enrollments")
	public Map<String, List<EnrollmentsResponseDto>> getListofEnrollments(@PathVariable("studentId") Integer studentId)
			throws StudentNotFoundException {
		return enrollmentService.getListofEnrollments(studentId);
	}

	/**
	 * This method updates the training if existing user wants to change the
	 * existing trainingId
	 * 
	 * @author Manisha
	 * @param updateEnrollmentRequestDto
	 * 
	 * @throws InvalidEnrollmentIdException when EnrollmentID not found in database
	 *                                      & DuplicateEnrollmentException when user
	 *                                      is updating with same existing session
	 * @return UpdateEnrollmentResponseDto will return the response of successfully
	 *         updated the Training
	 */
	@PutMapping("/enrollments/re-enroll")
	public ResponseEntity<UpdateEnrollmentResponseDto> updateEnrollment(
			@RequestBody UpdateEnrollmentRequestDto updateEnrollmentRequestDto)
			throws InvalidEnrollmentIdException, DuplicateEnrollmentException {
		logger.info(GrowthAcademyConstants.ENROLLMENT_CONTROLLER);
		return new ResponseEntity<>(enrollmentService.updateEnrollment(updateEnrollmentRequestDto), HttpStatus.OK);
	}

	@PostMapping("/cancel/{enrollmentId}")
	public ResponseEntity<CancelEnrollmentResponseDTO> cancelEnrollment(
			@RequestParam("enrollmentId") Integer enrollmentId) throws EnrollmentIdNotFoundException {
		logger.info(GrowthAcademyConstants.ENROLLMENT_INFO_NOT_EXIST);
		CancelEnrollmentResponseDTO enrollment = enrollmentService.cancelEnrollment(enrollmentId);
		return new ResponseEntity<>(enrollment, HttpStatus.OK);
	}

}
