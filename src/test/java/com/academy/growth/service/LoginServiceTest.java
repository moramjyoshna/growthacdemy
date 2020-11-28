package com.academy.growth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.academy.growth.controller.LoginController;
import com.academy.growth.dto.LoginRequestDTO;
import com.academy.growth.dto.LoginResponseDTO;
import com.academy.growth.entity.Student;
import com.academy.growth.exception.AuthenticationFailedException;
import com.academy.growth.exception.InvalidEmailIdException;
import com.academy.growth.repository.StudentRepository;
import com.academy.growth.service.LoginServiceImpl;
import com.academy.growth.util.GrowthAcademyConstants;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {
	@InjectMocks
	LoginServiceImpl loginServiceImpl;
	
	@Mock
	StudentRepository studentRepository;
	
	@Test
	public void testLoginOk() throws InvalidEmailIdException, AuthenticationFailedException{
		LoginRequestDTO loginRequestDto = new LoginRequestDTO();
		loginRequestDto.setEmailId("sravani@gmail.com");
		loginRequestDto.setPassword("sravani");
		Student student = new Student();
		Mockito.when(studentRepository.findByEmailIdAndPassword(loginRequestDto.getEmailId(), loginRequestDto.getPassword())).
		thenReturn(Optional.of(student));
		LoginResponseDTO loginResponseDTO = loginServiceImpl.studentLogin(loginRequestDto);
		assertEquals(GrowthAcademyConstants.LOGIN_SUCCESS, loginResponseDTO.getMessage());
		assertEquals(GrowthAcademyConstants.STATUS_CODE_OK, loginResponseDTO.getStatusCode());
		
	}
	@Test(expected = InvalidEmailIdException.class)
	public void testInvalidMailId() throws InvalidEmailIdException, AuthenticationFailedException{
		LoginRequestDTO loginRequestDto = new LoginRequestDTO();
		loginRequestDto.setEmailId("sravanigmail.com");
		loginServiceImpl.studentLogin(loginRequestDto);
	}
	@Test(expected = AuthenticationFailedException.class)
	public void testAuthenticationFailed() throws InvalidEmailIdException, AuthenticationFailedException{
		LoginRequestDTO loginRequestDto = new LoginRequestDTO();
		loginRequestDto.setEmailId("sravani@gmail.com");
		loginRequestDto.setPassword("sravani");
		loginServiceImpl.studentLogin(loginRequestDto);
	}

}
