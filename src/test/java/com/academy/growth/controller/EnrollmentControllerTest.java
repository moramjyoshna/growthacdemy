package com.academy.growth.controller;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.academy.growth.dto.UpdateEnrollmentRequestDto;
import com.academy.growth.dto.UpdateEnrollmentResponseDto;
import com.academy.growth.exception.DuplicateEnrollmentException;
import com.academy.growth.exception.InvalidEnrollmentIdException;
import com.academy.growth.service.EnrollmentService;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class EnrollmentControllerTest {

	@Mock
	EnrollmentService enrollmentService;

	@InjectMocks
	EnrollmentController EnrollmentController;

	UpdateEnrollmentRequestDto updateEnrollmentRequestDto = new UpdateEnrollmentRequestDto();
	UpdateEnrollmentRequestDto updateEnrollmentRequestDto1 = new UpdateEnrollmentRequestDto();

	//Enrollment enrollmentRecord = new Enrollment();
	UpdateEnrollmentResponseDto updateEnrollmentResponseDto = new UpdateEnrollmentResponseDto();

	@BeforeAll
	public void setup() {
		updateEnrollmentRequestDto.setEnrollmentId(1);
		updateEnrollmentRequestDto.setTrainingId(1);
		
		//enrollmentRecord.setTrainingId(1);
		updateEnrollmentRequestDto1.setEnrollmentId(1);
		updateEnrollmentRequestDto1.setTrainingId(2);
		updateEnrollmentResponseDto.setMessage("Success");
		updateEnrollmentResponseDto.setStatusCode("200");
	}
	
	@Test
	void testUpdateEnrollmentSuccess() throws InvalidEnrollmentIdException, DuplicateEnrollmentException {
		Mockito.when(enrollmentService.updateEnrollment(Mockito.any())).thenReturn(updateEnrollmentResponseDto);
		UpdateEnrollmentResponseDto actual = enrollmentService.updateEnrollment(updateEnrollmentRequestDto);
		Assert.assertEquals("200", actual.getStatusCode());
	}

	@Test
	void testUpdateEnrollmentFail() throws InvalidEnrollmentIdException, DuplicateEnrollmentException {
		Mockito.when(enrollmentService.updateEnrollment(Mockito.any())).thenReturn(updateEnrollmentResponseDto);
		UpdateEnrollmentResponseDto actual = enrollmentService.updateEnrollment(updateEnrollmentRequestDto1);
		Assert.assertEquals("200", actual.getStatusCode());
	}
}
