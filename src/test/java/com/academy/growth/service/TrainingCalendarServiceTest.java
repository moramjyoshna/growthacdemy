package com.academy.growth.service;

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

import com.academy.growth.entity.TrainingCalendar;
import com.academy.growth.repository.TrainingCalendarRepository;

@RunWith(MockitoJUnitRunner.class)
public class TrainingCalendarServiceTest {

	@Mock
	TrainingCalendarRepository trainingCalendarRepository;
	@InjectMocks
	TrainingCalendarServiceImpl trainingCalendarServiceImpl;

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

	}

	@Test
	public void testGetHospital() {

		trainingList.add(trainingCalendar);
		Mockito.when(trainingCalendarRepository.findAll()).thenReturn(trainingList);
		trainingInfo = trainingCalendarServiceImpl.getTrainings();
		assertNotNull(trainingInfo);
	}

}
