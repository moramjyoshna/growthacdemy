package com.academy.growth.service;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academy.growth.exception.AuthenticationFailedException;
import com.academy.growth.exception.InvalidEmailIdException;
import com.academy.growth.dto.LoginRequestDTO;
import com.academy.growth.dto.LoginResponseDTO;
import com.academy.growth.entity.Student;
import com.academy.growth.repository.StudentRepository;
import com.academy.growth.util.GrowthAcademyConstants;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	StudentRepository studentRepository;

	@Override
	public LoginResponseDTO studentLogin(LoginRequestDTO loginRequestDto) throws InvalidEmailIdException, AuthenticationFailedException{
		if (!validEmailId(loginRequestDto.getEmailId())) {
			throw new InvalidEmailIdException(GrowthAcademyConstants.INVALID_EMAIL);
		}
		Optional<Student> student = studentRepository.
				findByEmailIdAndPassword(loginRequestDto.getEmailId(), loginRequestDto.getPassword());
		if(!student.isPresent()) {
			throw new AuthenticationFailedException(GrowthAcademyConstants.INVALID_STUDENT);
		}
			LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
			loginResponseDTO.setMessage(GrowthAcademyConstants.LOGIN_SUCCESS);
			loginResponseDTO.setStatusCode(200);

		return loginResponseDTO;

	}
	private boolean validEmailId(String email) {
		Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		java.util.regex.Matcher m = p.matcher(email);
		return (m.find() && m.group().equals(email));
	}
}
