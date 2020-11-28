package com.academy.growth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academy.growth.dto.UpdateEnrollmentRequestDto;
import com.academy.growth.dto.UpdateEnrollmentResponseDto;
import com.academy.growth.entity.Enrollment;
import com.academy.growth.exception.DuplicateEnrollmentException;
import com.academy.growth.exception.InvalidEnrollmentIdException;
import com.academy.growth.repository.EnrollmentRepository;
import com.academy.growth.util.GrowthAcademyConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EnrollmentServiceImpl implements EnrollmentService {

	@Autowired
	EnrollmentRepository enrollmentRepository;

	@Override
	public UpdateEnrollmentResponseDto updateEnrollment(UpdateEnrollmentRequestDto updateEnrollmentRequestDto) throws InvalidEnrollmentIdException, DuplicateEnrollmentException {
		UpdateEnrollmentResponseDto updateEnrollmentResponseDto = new UpdateEnrollmentResponseDto();
		
		Optional<Enrollment> enrolledRecord = enrollmentRepository.findByEnrollmentId(updateEnrollmentRequestDto.getEnrollmentId()); 
		  if(enrolledRecord.isPresent()) {
			 if (updateEnrollmentRequestDto.getTrainingId() != enrolledRecord.get().getTrainingId()) 
			 {
			  enrolledRecord.get().setTrainingId(updateEnrollmentRequestDto.getTrainingId());
			  enrollmentRepository.save(enrolledRecord.get());
			  }else throw new DuplicateEnrollmentException(GrowthAcademyConstants.ALREADY_ENROLLED_EXCEPTION);
			}else {
				throw new InvalidEnrollmentIdException(GrowthAcademyConstants.NO_ENROLLMENT_ID_EXCEPTION);
			}
		updateEnrollmentResponseDto.setMessage("Success");
		updateEnrollmentResponseDto.setStatusCode("200");

		return updateEnrollmentResponseDto;		
	}
}
