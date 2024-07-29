package com.dbp.odontogramservice.repository;

import com.dbp.odontogramservice.model.Odontogram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OdontogramRepository extends JpaRepository<Odontogram, Long> {
    List<Odontogram> findByPatientId(Long patientId);
}