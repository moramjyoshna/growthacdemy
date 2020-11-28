
package com.academy.growth.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.academy.growth.dto.CancelEnrollmentResponseDTO;
import com.academy.growth.exception.EnrollmentIdNotFoundException;
import com.academy.growth.repository.EnrollmentRepository;
import com.academy.growth.util.GrowthAcademyConstants;

public class EnrollmentServiceImpl implements EnrollmentService {

	@Autowired
	EnrollmentRepository enrollmentRepository;

	private static final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);

	@Override
	public Optional<CancelEnrollmentResponseDTO> cancelEnrollment(Integer enrollmentId)
			throws EnrollmentIdNotFoundException {

		logger.info(GrowthAcademyConstants.CANCEL_ENROLLMENTT_SERVICE);
		Optional<CancelEnrollmentResponseDTO> enrollment = enrollmentRepository.findByEnrollmentId(enrollmentId);
		if (!enrollment.isPresent()) {
			throw new EnrollmentIdNotFoundException(GrowthAcademyConstants.ENROLLMENT_INFO_NOT_EXIST);
		}
		if(enrollment.get().getEnrollmentStatus().equals("CANCELLED")) {
			throw new EnrollmentIdNotFoundException(GrowthAcademyConstants.ENROLLEMENT_ALREADY_CANCELLED);
		}
		enrollment.get().setEnrollmentStatus("CANCELLED");
		return enrollment;
	}

}
