package com.requestproducer.repository;

import com.requestproducer.entity.CollegeAdmission;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CollegeAdmissionRepository extends JpaRepository<CollegeAdmission, Long> {
    boolean existsByHallTicketNumber(String hallTicketNumber);

    boolean existsByEmail(String email);

    Long countByInterestedStream(String interestedStream);

    CollegeAdmission findByHallTicketNumber(String hallTicketNumber);

    @Transactional
    @Modifying
    @Query(value = "update COLLEGE_ADMISSION_REQUEST set status = ?1 where HALL_TICKET_NUMBER = ?2", nativeQuery = true)
    int updateCollegeAdmissionByHallTicketNumber(String status, String hallTicketNumber);
}
