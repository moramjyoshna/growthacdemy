package com.academy.growth.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
	private String message;
	private Integer statusCode;

	public ErrorResponse(String message, int value) {
		this.message = message;
		this.statusCode = value;
	}

}
