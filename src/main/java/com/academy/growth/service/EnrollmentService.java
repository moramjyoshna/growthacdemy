package com.academy.growth.service;

import java.util.Optional;

import com.academy.growth.dto.CancelEnrollmentResponseDTO;
import com.academy.growth.dto.EnrollmentRequestDto;
import com.academy.growth.dto.EnrollmentResponseDto;
import com.academy.growth.exception.EnrollmentException;
import com.academy.growth.exception.EnrollmentIdNotFoundException;

public interface EnrollmentService {

	public EnrollmentResponseDto enroll(EnrollmentRequestDto enrollmentRequestDto) throws EnrollmentException;

	Optional<CancelEnrollmentResponseDTO> cancelEnrollment(Integer enrollmentId) throws EnrollmentIdNotFoundException;

}
