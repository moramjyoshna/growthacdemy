package com.academy.growth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academy.growth.entity.TrainingCalendar;

@Repository
public interface TrainingCalendarRepository extends JpaRepository<TrainingCalendar, Integer>{

}
