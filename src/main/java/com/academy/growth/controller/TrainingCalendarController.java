package com.academy.growth.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academy.growth.entity.TrainingCalendar;
import com.academy.growth.exception.TrainingInfoNotFoundException;
import com.academy.growth.service.TrainingCalendarService;
import com.academy.growth.util.GrowthAcademyConstants;

@RestController
@RequestMapping("/")
public class TrainingCalendarController {

	@Autowired
	TrainingCalendarService trainingService;

	private static final Logger logger = LoggerFactory.getLogger(TrainingCalendarController.class);

	@GetMapping("trainings")
	public ResponseEntity<List<TrainingCalendar>> getTrainingList() throws TrainingInfoNotFoundException {
		logger.info(GrowthAcademyConstants.GET_TRAINING_LIST_CONTROLLER);
		Optional<List<TrainingCalendar>> training = trainingService.getTrainings();
		List<TrainingCalendar> trainingList = new ArrayList<>();
		if (training.isPresent()) {
			trainingList = training.get();
			if (trainingList.isEmpty()) {
				throw new TrainingInfoNotFoundException(GrowthAcademyConstants.TRAINING_INFO_NOT_EXIST);
			}
		}
		return new ResponseEntity<>(trainingList, HttpStatus.OK);

	}
}
