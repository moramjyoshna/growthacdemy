package com.academy.growth.controller;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.academy.growth.entity.TrainingCalendar;
import com.academy.growth.exception.TrainingInfoNotFoundException;
import com.academy.growth.service.TrainingCalendarServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TrainingCalendarControllerTest {

	@Mock
	TrainingCalendarServiceImpl trainingCalendarServiceImpl;

	@InjectMocks
	TrainingCalendarController trainingCalendarController;

	TrainingCalendar trainingCalendar;

	Optional<List<TrainingCalendar>> trainingInfo;

	List<TrainingCalendar> trainingList;

	@Before
	public void initiateData() {

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

	@Test(expected = TrainingInfoNotFoundException.class)
	public void testNegativeGetTrainingList() throws TrainingInfoNotFoundException {
		trainingList = new ArrayList<>();
		trainingInfo = Optional.ofNullable(trainingList);
		Mockito.when(trainingCalendarServiceImpl.getTrainings()).thenReturn(trainingInfo);
		ResponseEntity<List<TrainingCalendar>> trainingResponseList = trainingCalendarController.getTrainingList();
		assertNotNull(trainingResponseList);
	}

}
