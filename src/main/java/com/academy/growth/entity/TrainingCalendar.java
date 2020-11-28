package com.academy.growth.entity;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class TrainingCalendar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer trainingId;
	private String trainerName;
	private Integer tainingDuration;
	private Date startDate;
	private Date endDate;
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

}
