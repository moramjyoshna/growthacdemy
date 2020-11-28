package com.academy.growth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.academy.growth.dto.LoginRequestDTO;
import com.academy.growth.dto.LoginResponseDTO;
import com.academy.growth.entity.Student;
import com.academy.growth.exception.AuthenticationFailedException;
import com.academy.growth.exception.InvalidEmailIdException;
import com.academy.growth.repository.StudentRepository;
import com.academy.growth.util.GrowthAcademyConstants;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class LoginServiceTest {
	@InjectMocks
	LoginServiceImpl loginServiceImpl;

	@Mock
	StudentRepository studentRepository;

	@Test
	public void testLoginOk() throws InvalidEmailIdException, AuthenticationFailedException {
		LoginRequestDTO loginRequestDto = new LoginRequestDTO();
		loginRequestDto.setEmailId("sravani@gmail.com");
		loginRequestDto.setPassword("sravani");
		Student student = new Student();
		Mockito.when(
				studentRepository.findByEmailIdAndPassword(loginRequestDto.getEmailId(), loginRequestDto.getPassword()))
				.thenReturn(Optional.of(student));
		LoginResponseDTO loginResponseDTO = loginServiceImpl.studentLogin(loginRequestDto);
		assertEquals(GrowthAcademyConstants.LOGIN_SUCCESS, loginResponseDTO.getMessage());
		assertEquals(GrowthAcademyConstants.STATUS_CODE_OK, loginResponseDTO.getStatusCode());

	}

	@Test
	public void testInvalidMailId() throws InvalidEmailIdException, AuthenticationFailedException {
		LoginRequestDTO loginRequestDto = new LoginRequestDTO();
		loginRequestDto.setEmailId("sravanigmail.com");
		Assertions.assertThrows(InvalidEmailIdException.class, () -> {
			loginServiceImpl.studentLogin(loginRequestDto);
		});

	}

	@Test
	public void testAuthenticationFailed() throws InvalidEmailIdException, AuthenticationFailedException {
		LoginRequestDTO loginRequestDto = new LoginRequestDTO();
		loginRequestDto.setEmailId("sravani@gmail.com");
		loginRequestDto.setPassword("sravani");
		Assertions.assertThrows(AuthenticationFailedException.class, () -> {
			loginServiceImpl.studentLogin(loginRequestDto);
		});
	}

}
