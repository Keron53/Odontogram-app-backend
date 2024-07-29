package com.dbp.recordmicroservice.controller;

import com.dbp.recordmicroservice.model.Record;
import com.dbp.recordmicroservice.repository.RecordRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/records")
@Tag(name= "Records Resources")
public class RecordController {

    @Autowired
    private RecordRepository recordRepository;

    @Operation(summary = "Obtiene todas las fichas tecnicas")
    @GetMapping
    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    @Operation(summary = "Obtiene las fichas tecnicas por id")
    @GetMapping("/{id}")
    public ResponseEntity<Record> getRecordById(@PathVariable Long id) {
        Optional<Record> record = recordRepository.findById(id);
        return record.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtiene las fichas técnicas por paciente")
    @GetMapping("/patient/{patientId}")
    public List<Record> getRecordsByPatientId(@PathVariable Long patientId) {
        return recordRepository.findByPatientId(patientId);
    }


    @Operation(summary = "Publica una ficha tecnica")
    @PostMapping
    public Record createRecord(@RequestBody Record record) {
        return recordRepository.save(record);
    }

    @Operation(summary = "Actualiza una ficha técnica de acuerdo con el id")
    @PutMapping("/{id}")
    public ResponseEntity<Record> updateRecord(@PathVariable Long id, @RequestBody Record recordDetails) {
        Optional<Record> optionalRecord = recordRepository.findById(id);
        if (optionalRecord.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Record record = optionalRecord.get();
        record.setTreatment(recordDetails.getTreatment());
        record.setDate(recordDetails.getDate());
        record.setCost(recordDetails.getCost());
        record.setPaymentStatus(recordDetails.getPaymentStatus());
        Record updatedRecord = recordRepository.save(record);
        return ResponseEntity.ok(updatedRecord);
    }

    @Operation(summary = "Borra una ficha técnica")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        recordRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
