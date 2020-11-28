package com.academy.growth.service;

import com.academy.growth.dto.UpdateEnrollmentRequestDto;
import com.academy.growth.dto.UpdateEnrollmentResponseDto;
import com.academy.growth.exception.DuplicateEnrollmentException;
import com.academy.growth.exception.InvalidEnrollmentIdException;

public interface EnrollmentService {
	
	public UpdateEnrollmentResponseDto updateEnrollment(UpdateEnrollmentRequestDto updateEnrollmentResponseDto) throws InvalidEnrollmentIdException, DuplicateEnrollmentException;

}
