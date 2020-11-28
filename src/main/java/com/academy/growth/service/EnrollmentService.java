
package com.academy.growth.service;

import java.util.Optional;

import com.academy.growth.dto.CancelEnrollmentResponseDTO;
import com.academy.growth.exception.EnrollmentIdNotFoundException;

public interface EnrollmentService {

	Optional<CancelEnrollmentResponseDTO> cancelEnrollment(Integer enrollmentId) throws EnrollmentIdNotFoundException;

}
