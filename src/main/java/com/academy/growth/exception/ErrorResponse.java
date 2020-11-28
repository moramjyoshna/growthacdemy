package com.academy.growth.exception;

public class ErrorResponse {
	private String message;
	private Integer statusCode;

	public ErrorResponse(String message, int value) {
		this.message = message;
		this.statusCode = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

}
