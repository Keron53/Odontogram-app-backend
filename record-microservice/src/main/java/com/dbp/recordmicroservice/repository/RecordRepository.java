package com.dbp.recordmicroservice.repository;

import com.dbp.recordmicroservice.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByPatientId(Long patientId);
}
