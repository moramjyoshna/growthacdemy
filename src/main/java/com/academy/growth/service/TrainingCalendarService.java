package com.academy.growth.service;

import java.util.List;
import java.util.Optional;

import com.academy.growth.entity.TrainingCalendar;

public interface TrainingCalendarService {

	Optional<List<TrainingCalendar>> getTrainings();
}
