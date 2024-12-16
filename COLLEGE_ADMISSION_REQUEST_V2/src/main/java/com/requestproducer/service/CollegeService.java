package com.requestproducer.service;

import com.requestproducer.entity.CollegeAdmission;
import com.requestproducer.repository.CollegeAdmissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CollegeService {

    @Autowired
    private CollegeAdmissionRepository repository;

    public List<CollegeAdmission> getAllEntities() {
        return repository.findAll();
    }

    public void saveAdmission(CollegeAdmission admission) {
        repository.save(admission);
    }

    public boolean checkDuplicateHallTicketEntry(String hallTicketNumber){
        return repository.existsByHallTicketNumber(hallTicketNumber);
    }
    public boolean checkDuplicateEmailEntry(String email){
        return repository.existsByEmail(email);
    }

    public Long getNumberOfInterestedStreams(String stream){
        return repository.countByInterestedStream(stream);
    }

    public int updateDatabaseStatus(CollegeAdmission collegeAdmission){
     return repository.updateCollegeAdmissionByHallTicketNumber("PROCESSED", collegeAdmission.getHallTicketNumber());
    }

    public CollegeAdmission findEntryByHallTicketNumber(String number){
        return repository.findByHallTicketNumber(number);
    }
    // Other service methods
}

