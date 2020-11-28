package com.academy.growth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academy.growth.entity.TrainingCalendar;

@Repository
public interface TrainingCalendarRepository extends JpaRepository<TrainingCalendar, Integer>{

	public Optional<TrainingCalendar> findByTrainingId(Integer trainingId);
	
}
