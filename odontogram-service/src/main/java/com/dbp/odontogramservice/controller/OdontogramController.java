package com.dbp.odontogramservice.controller;

import com.dbp.odontogramservice.model.Odontogram;
import com.dbp.odontogramservice.repository.OdontogramRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontograms")
@Tag(name = "Odontograms Resources")
public class OdontogramController {

    @Autowired
    private OdontogramRepository odontogramRepository;

    @Operation(summary = "Obtiene todos los odontogramas")
    @GetMapping
    public List<Odontogram> getAllOdontograms() {
        return odontogramRepository.findAll();
    }

    @Operation(summary = "Obtiene los odontogramas por id")
    @GetMapping("/{id}")
    public ResponseEntity<Odontogram> getOdontogramById(@PathVariable Long id) {
        Optional<Odontogram> odontogram = odontogramRepository.findById(id);
        return odontogram.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Obtiene los odontogramas por ID de paciente")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Odontogram>> getOdontogramsByPatientId(@PathVariable Long patientId) {
        List<Odontogram> odontograms = odontogramRepository.findByPatientId(patientId);
        return ResponseEntity.ok(odontograms);
    }

    @Operation(summary = "Publica un odontograma")
    @PostMapping
    public Odontogram createOdontogram(@RequestBody Odontogram odontogram) {
        return odontogramRepository.save(odontogram);
    }

    @Operation(summary = "Actualiza un odontograma de acuerdo con el id")
    @PutMapping("/{id}")
    public ResponseEntity<Odontogram> updateOdontogram(@PathVariable Long id, @RequestBody Odontogram odontogramDetails) {
        Optional<Odontogram> optionalOdontogram = odontogramRepository.findById(id);
        if (optionalOdontogram.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Odontogram odontogram = optionalOdontogram.get();
        odontogram.setToothNumber(odontogramDetails.getToothNumber());
        odontogram.setCondition(odontogramDetails.getCondition());
        odontogram.setTreatment(odontogramDetails.getTreatment());
        Odontogram updatedOdontogram = odontogramRepository.save(odontogram);
        return ResponseEntity.ok(updatedOdontogram);
    }

    @Operation(summary = "Borra un odontograma de acuerdo con el id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOdontogram(@PathVariable Long id) {
        odontogramRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
