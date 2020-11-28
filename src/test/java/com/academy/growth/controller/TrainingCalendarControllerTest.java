package com.academy.growth.controller;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.academy.growth.entity.TrainingCalendar;
import com.academy.growth.exception.TrainingInfoNotFoundException;
import com.academy.growth.service.TrainingCalendarServiceImpl;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TrainingCalendarControllerTest {

	@Mock
	TrainingCalendarServiceImpl trainingCalendarServiceImpl;

	@InjectMocks
	TrainingCalendarController trainingCalendarController;

	TrainingCalendar trainingCalendar;

	Optional<List<TrainingCalendar>> trainingInfo;

	List<TrainingCalendar> trainingList;

	@BeforeAll
	public void setup() {

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.set(Calendar.MONTH, 11);
		c1.set(Calendar.DATE, 28);
		c1.set(Calendar.YEAR, 2020);
		c2.set(Calendar.DATE, 4);
		c2.set(Calendar.MONTH, 12);
		c2.set(Calendar.YEAR, 2020);
		Date startDate = c1.getTime();
		Date endDate = c2.getTime();

		trainingCalendar = new TrainingCalendar();
		trainingCalendar.setTrainingId(1);
		trainingCalendar.setTrainerName("Alicia");
		trainingCalendar.setTainingDuration(7);
		trainingCalendar.setCourseCode("JAVA01");
		trainingCalendar.setStartDate(startDate);
		trainingCalendar.setEndDate(endDate);

		trainingList = new ArrayList<>();

		trainingList.add(trainingCalendar);
	}

	@Test
	public void testGetTrainingList() throws TrainingInfoNotFoundException {

		trainingInfo = Optional.of(trainingList);
		Mockito.when(trainingCalendarServiceImpl.getTrainings()).thenReturn(trainingInfo);
		ResponseEntity<List<TrainingCalendar>> trainingResponseList = trainingCalendarController.getTrainingList();
		assertNotNull(trainingResponseList);
	}

	@Test
	public void testNegativeGetTrainingList() throws TrainingInfoNotFoundException {
		trainingList = new ArrayList<>();
		trainingInfo = Optional.ofNullable(trainingList);
		Mockito.when(trainingCalendarServiceImpl.getTrainings()).thenReturn(trainingInfo);
		Assertions.assertThrows(TrainingInfoNotFoundException.class, () -> {
			trainingCalendarController.getTrainingList();
		});

	}

}
