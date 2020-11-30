package com.academy.growth.util;

public class GrowthAcademyConstants {
	
	private GrowthAcademyConstants() {
		throw new IllegalStateException("Utility class");
	}
	
	public static final String TRAINING_DOES_NOT_EXISTS = "No such training exists presently";
	public static final String COURSE_DOES_NOT_EXISTS = "No such course exists presently";
	public static final String COURSE_ENROLLMENT_EXCEEDED = "Soory, you have exceeded the Scheduled courses you can enroll";
	public static final String CANNOT_EROLL_TO_COURSE = "Sorry, You cannot enroll to course now";
	public static final String INVALID_EMAIL = "please enter a valid email Id";
	public static final String INVALID_STUDENT = "Invalid Username/Password";
	public static final String LOGIN_SUCCESS = "Student logged in successfully";
	public static final String GET_TRAINING_LIST_SERVICE = "Inside training service";
	public static final String GET_TRAINING_LIST_CONTROLLER = "Inside Training controller";
	public static final String TRAINING_INFO_NOT_EXIST = "Training details not exists";
	public static final String CANCEL_ENROLLMENT_CONTROLLER = "Inside cancel enrollment controller";
	public static final String ENROLLMENT_INFO_NOT_EXIST = "Enrollment ID doesnot exists";
	public static final String CANCEL_ENROLLMENTT_SERVICE = "Inside cancel Enrollment Service";
	public static final Integer STATUS_CODE_OK = 200;
	public static final String LOGIN_SERVICE = "Inside training service";
	public static final String LOGIN_CONTROLLER = "Inside Training controller";
	public static final String ENROLLEMENT_ALREADY_CANCELLED="Enrollment already cancelled";
	public static final String ENROLLMENT_CONTROLLER = "Inside Enrollment controller";
	public static final String STUDENT_NOT_FOUND = "Student ID doesn't exists";
	public static final String SCHEDULED = "SCHEDULED";
	public static final String IN_PROGRESS = "IN PROGRESS";
	public static final String COURSE_ALREADY_ENROLLED = "You have already enrolled to the course";
	public static final String ENROLL_SUCCESS= "Enrolled sucessfully";
	public static final String NO_ENROLLMENT_ID_EXCEPTION = "No such enrollment id found";
	public static final String ALREADY_ENROLLED_EXCEPTION = "Already enrolled with the same course";
	public static final String ENROLLMENT_ID_NOT_EXIST = "Inside Enrollment ID doesn't exist";
	public static final String ENROLLMENT_UPDATE_SERVICE = "Inside service updateEnrollment method";
	public static final String ENROLLMENT_GET_SERVICE = "Displays Enrolled History for specified student";

}
