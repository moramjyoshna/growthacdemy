
package com.academy.growth.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.academy.growth.dto.CancelEnrollmentResponseDTO;
import com.academy.growth.exception.EnrollmentIdNotFoundException;
import com.academy.growth.service.EnrollmentService;
import com.academy.growth.util.GrowthAcademyConstants;

public class EnrollmentController {

	@Autowired
	EnrollmentService enrollmentService;
	private static final Logger logger = LoggerFactory.getLogger(EnrollmentController.class);

	@PostMapping("/{diagnosisId}")
	public ResponseEntity<Optional<CancelEnrollmentResponseDTO>> cancelEnrollment(@PathVariable Integer enrollmentId)
			throws EnrollmentIdNotFoundException {
		logger.info(GrowthAcademyConstants.ENROLLMENT_INFO_NOT_EXIST);
		Optional<CancelEnrollmentResponseDTO> enrollment = enrollmentService.cancelEnrollment(enrollmentId);
		return new ResponseEntity<>(enrollment, HttpStatus.OK);

	}
}
