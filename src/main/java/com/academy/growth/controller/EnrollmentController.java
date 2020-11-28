package com.academy.growth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.academy.growth.dto.EnrollmentRequestDto;
import com.academy.growth.dto.EnrollmentResponseDto;
import com.academy.growth.exception.EnrollmentException;
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

}
