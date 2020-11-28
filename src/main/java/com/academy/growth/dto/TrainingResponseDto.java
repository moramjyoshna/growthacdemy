package com.academy.growth.dto;

import java.sql.Timestamp;

public class TrainingResponseDto {

	private Integer trainingId;
	private String trainerName;
	private Integer tainingDuration;
	private Timestamp startDate;
	private Timestamp endDate;
	private String courseCode;
	public Integer getTrainingId() {
		return trainingId;
	}
	public void setTrainingId(Integer trainingId) {
		this.trainingId = trainingId;
	}
	public String getTrainerName() {
		return trainerName;
	}
	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}
	public Integer getTainingDuration() {
		return tainingDuration;
	}
	public void setTainingDuration(Integer tainingDuration) {
		this.tainingDuration = tainingDuration;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
}
