package com.academy.growth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

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
import com.academy.growth.entity.Enrollment;
import com.academy.growth.exception.DuplicateEnrollmentException;
import com.academy.growth.exception.InvalidEnrollmentIdException;
import com.academy.growth.repository.EnrollmentRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class EnrollmentServiceImplTest {

	@Mock
	EnrollmentRepository enrollmentRepository;

	@InjectMocks
	EnrollmentServiceImpl enrollmentServiceImpl;

	UpdateEnrollmentRequestDto updateEnrollmentRequestDto = new UpdateEnrollmentRequestDto();
	UpdateEnrollmentRequestDto updateEnrollmentRequestDto1 = new UpdateEnrollmentRequestDto();

	Enrollment enrollmentRecord = new Enrollment();

	UpdateEnrollmentResponseDto updateEnrollmentResponseDto = new UpdateEnrollmentResponseDto();

	@BeforeAll
	public void setup() {
		updateEnrollmentRequestDto.setEnrollmentId(1);
		updateEnrollmentRequestDto.setTrainingId(2);
		
		enrollmentRecord.setTrainingId(1);
		updateEnrollmentRequestDto1.setEnrollmentId(1);
		updateEnrollmentRequestDto1.setTrainingId(1);

		updateEnrollmentResponseDto.setMessage("Success");
		updateEnrollmentResponseDto.setStatusCode("200");
	}

	@Test
	void testUpdateEnrollmentSuccess() throws InvalidEnrollmentIdException, DuplicateEnrollmentException {
		Mockito.when(enrollmentRepository.findByEnrollmentId(Mockito.any())).thenReturn(Optional.of(enrollmentRecord));
		UpdateEnrollmentResponseDto actual = enrollmentServiceImpl.updateEnrollment(updateEnrollmentRequestDto);
	    assertThat(Optional.of(enrollmentRecord)).isNotEmpty();
		Assert.assertEquals("200", actual.getStatusCode());
	}

	/*
	 * @Test void testUpdateEnrollmentFail() {
	 * Mockito.when(enrollmentRepository.findByEnrollmentId(Mockito.any())).
	 * thenReturn(Optional.of(enrollmentRecord)); Exception exception =
	 * assertThrows(DuplicateEnrollmentException.class, () ->
	 * enrollmentServiceImpl.updateEnrollment(updateEnrollmentRequestDto1));
	 * assertEquals("Already enrolled with the same course",
	 * exception.getMessage()); }
	 */
	  
}
