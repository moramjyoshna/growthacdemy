package com.academy.growth.service;

import com.academy.growth.dto.EnrollmentRequestDto;
import com.academy.growth.dto.EnrollmentResponseDto;
import com.academy.growth.exception.EnrollmentException;

public interface EnrollmentService {
	
	public EnrollmentResponseDto enroll(EnrollmentRequestDto enrollmentRequestDto) throws EnrollmentException;

}
