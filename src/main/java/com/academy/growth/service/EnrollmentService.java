package com.academy.growth.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.academy.growth.dto.CancelEnrollmentResponseDTO;
import com.academy.growth.dto.EnrollmentRequestDto;
import com.academy.growth.dto.EnrollmentResponseDto;
import com.academy.growth.dto.EnrollmentsResponseDto;
import com.academy.growth.exception.EnrollmentException;
import com.academy.growth.exception.EnrollmentIdNotFoundException;
import com.academy.growth.exception.StudentNotFoundException;

public interface EnrollmentService {

	public EnrollmentResponseDto enroll(EnrollmentRequestDto enrollmentRequestDto) throws EnrollmentException;

	Optional<CancelEnrollmentResponseDTO> cancelEnrollment(Integer enrollmentId) throws EnrollmentIdNotFoundException;

	public Map<String, List<EnrollmentsResponseDto>> getListofEnrollments(Integer studentId)
			throws StudentNotFoundException;
}
