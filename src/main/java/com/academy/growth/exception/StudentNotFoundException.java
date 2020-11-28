package com.academy.growth.exception;

import java.io.Serializable;

public class StudentNotFoundException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;

	public StudentNotFoundException(String message) {
		super(message);
}
}