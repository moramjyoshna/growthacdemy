package com.academy.growth.dto;

import javax.validation.constraints.NotBlank;

public class EnrollmentRequestDto {
	
	
	private Integer trainingId;
	
	
	private Integer studentId;
	
	public Integer getTrainingId() {
		return trainingId;
	}
	public void setTrainingId(Integer trainingId) {
		this.trainingId = trainingId;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

}
