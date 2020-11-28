package com.academy.growth.controller;

import java.util.regex.Pattern;

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

@RestController
@RequestMapping("/student")
public class LoginController {
	@Autowired
	LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> studentLogin(@RequestBody LoginRequestDTO loginRequestDto)
			 throws InvalidEmailIdException,AuthenticationFailedException{
		LoginResponseDTO loginResponseDto = loginService.studentLogin(loginRequestDto);
		return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
	}
	
}
