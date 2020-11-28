/*
 * package com.academy.growth.service;
 * 
 * import java.util.Optional;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired;
 * 
 * import com.academy.growth.dto.CancelEnrollmentResponseDTO; import
 * com.academy.growth.repository.EnrollmentRepository; import
 * com.academy.growth.util.GrowthAcademyConstants;
 * 
 * public class EnrollmentServiceImpl implements EnrollmentService {
 * 
 * @Autowired EnrollmentRepository enrollmentRepository;
 * 
 * private static final Logger logger =
 * LoggerFactory.getLogger(EnrollmentServiceImpl.class);
 * 
 * @Override public Optional<CancelEnrollmentResponseDTO>
 * cancelEnrollment(Integer enrollmentId) {
 * 
 * logger.info(GrowthAcademyConstants.CANCEL_ENROLLMENTT_SERVICE);
 * CancelEnrollmentResponseDTO enrollment =
 * enrollmentRepository.findByEnrollmentId(enrollmentId); return
 * Optional.of(enrollment); }
 * 
 * }
 */