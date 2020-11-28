package com.academy.growth.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academy.growth.entity.TrainingCalendar;
import com.academy.growth.repository.TrainingCalendarRepository;
import com.academy.growth.util.GrowthAcademyConstants;

@Service
public class TrainingCalendarServiceImpl implements TrainingCalendarService {

	@Autowired
	TrainingCalendarRepository trainingCalendarRepository;
	private static final Logger logger = LoggerFactory.getLogger(TrainingCalendarServiceImpl.class);

	@Override
	public Optional<List<TrainingCalendar>> getTrainings() {
		logger.info(GrowthAcademyConstants.GET_TRAINING_LIST_SERVICE);
		List<TrainingCalendar> traininglist = trainingCalendarRepository.findAll();
		return Optional.of(traininglist);
	}

}
