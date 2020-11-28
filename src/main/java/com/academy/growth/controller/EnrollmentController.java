package com.academy.growth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academy.growth.dto.UpdateEnrollmentRequestDto;
import com.academy.growth.dto.UpdateEnrollmentResponseDto;
import com.academy.growth.exception.DuplicateEnrollmentException;
import com.academy.growth.exception.InvalidEnrollmentIdException;
import com.academy.growth.service.EnrollmentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/")
public class EnrollmentController {
	
	@Autowired
	EnrollmentService enrollmentService;

	@PutMapping("/enrollments/re-enroll")
	public ResponseEntity<UpdateEnrollmentResponseDto> updateEnrollment(@RequestBody UpdateEnrollmentRequestDto updateEnrollmentRequestDto) throws InvalidEnrollmentIdException, DuplicateEnrollmentException
	{
		return new ResponseEntity<>(enrollmentService.updateEnrollment(updateEnrollmentRequestDto), HttpStatus.OK);
	}
}
