package com.academy.growth.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.academy.growth.dto.EnrollmentRequestDto;
import com.academy.growth.dto.EnrollmentResponseDto;
import com.academy.growth.dto.EnrollmentsResponseDto;
import com.academy.growth.exception.EnrollmentException;
import com.academy.growth.exception.StudentNotFoundException;
import com.academy.growth.service.EnrollmentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EnrollmentController {

	@Autowired
	EnrollmentService enrollmentService;

	@PostMapping("/enroll-course")
	public ResponseEntity<EnrollmentResponseDto> courseEnrollment(
			 @RequestBody EnrollmentRequestDto enrollmentRequestDto)
			throws EnrollmentException {

		EnrollmentResponseDto enrollmentResponseDto = enrollmentService.enroll(enrollmentRequestDto);
		return new ResponseEntity<>(enrollmentResponseDto, HttpStatus.CREATED);
	}

	/**
	 * This method views the student's enrollment history grouped by the enrollment status
	 * 
	 * @author swathi
	 * @param studentId contains studentId
	 * 
	 * @throws StudentNotFoundException thrown when student not found
	 * @return EnrollmentsResponseDto contains enrollment history grouped by the enrollment status.
	 */
	@GetMapping("/enrollments/{studentId}")
	public Map<String, List<EnrollmentsResponseDto>> getListofEnrollments(@RequestParam("studentId") Integer studentId)
			throws StudentNotFoundException {
		return enrollmentService.getListofEnrollments(studentId);
	}
}
