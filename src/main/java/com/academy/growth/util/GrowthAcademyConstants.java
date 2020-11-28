package com.academy.growth.util;

public class GrowthAcademyConstants {
	public static final String INVALID_EMAIL = "please enter a valid email Id";
	public static final String INVALID_STUDENT = "Invalid Username/Password";
	public static final String LOGIN_SUCCESS = "Student logged in successfully";


	private GrowthAcademyConstants() {
		throw new IllegalStateException("Utility class");
	}

	public static final String GET_TRAINING_LIST_SERVICE = "Inside training service";
	public static final String GET_TRAINING_LIST_CONTROLLER = "Inside Training controller";
	public static final String TRAINING_INFO_NOT_EXIST = "Training details not exists";
}
