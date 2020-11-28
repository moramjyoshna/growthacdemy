package com.academy.growth.service;

import com.academy.growth.dto.LoginRequestDTO;
import com.academy.growth.dto.LoginResponseDTO;
import com.academy.growth.exception.AuthenticationFailedException;
import com.academy.growth.exception.InvalidEmailIdException;

public interface LoginService {
	public LoginResponseDTO studentLogin(LoginRequestDTO loginRequestDTO) throws InvalidEmailIdException, AuthenticationFailedException;

}
