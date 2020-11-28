package com.academy.growth.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.academy.growth.exception.AuthenticationFailedException;
import com.academy.growth.exception.InvalidEmailIdException;
import com.academy.growth.service.LoginService;
import com.academy.growth.util.GrowthAcademyConstants;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {
	@InjectMocks
	LoginController loginController;
	
	@Mock
	LoginService loginService;
	
	@Test
	public void testLogin() throws InvalidEmailIdException, AuthenticationFailedException{
		LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
		loginResponseDTO.setMessage(GrowthAcademyConstants.LOGIN_SUCCESS);
		loginResponseDTO.setStatusCode(200);
		LoginRequestDTO loginRequestDto = new LoginRequestDTO();
		Mockito.when(loginService.studentLogin(loginRequestDto)).thenReturn(loginResponseDTO);
		ResponseEntity<LoginResponseDTO> login = loginController.studentLogin(loginRequestDto);
		assertEquals(HttpStatus.OK, login.getStatusCode());
		assertEquals(GrowthAcademyConstants.LOGIN_SUCCESS, login.getBody().getMessage());
		assertEquals(GrowthAcademyConstants.STATUS_CODE_OK, login.getBody().getStatusCode());
		
	}

}
