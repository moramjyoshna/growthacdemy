package com.academy.growth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.academy.growth.dto.LoginRequestDTO;
import com.academy.growth.dto.LoginResponseDTO;
import com.academy.growth.exception.AuthenticationFailedException;
import com.academy.growth.exception.InvalidEmailIdException;
import com.academy.growth.service.LoginService;
import com.academy.growth.util.GrowthAcademyConstants;

@RestController
@RequestMapping("/student")
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> studentLogin(@RequestBody LoginRequestDTO loginRequestDto)
			throws InvalidEmailIdException, AuthenticationFailedException {
		logger.info(GrowthAcademyConstants.LOGIN_CONTROLLER);
		LoginResponseDTO loginResponseDto = loginService.studentLogin(loginRequestDto);
		return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
	}

}
