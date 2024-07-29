package com.dbp.patientservice.controller;

import com.dbp.patientservice.model.Patient;
import com.dbp.patientservice.repository.PatientRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
@Tag(name= "Patient Resources")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Operation(summary = "Obtiene a todos los pacientes")
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Operation(summary = "Obtiene un paciente por id")
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Publica a un paciente")
    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }

    @Operation(summary = "Actualiza un paciente de acuerdo al id")
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Patient patient = optionalPatient.get();
        patient.setFirstName(patientDetails.getFirstName());
        patient.setLastName(patientDetails.getLastName());
        patient.setEmail(patientDetails.getEmail());
        patient.setPhone(patientDetails.getPhone());
        patient.setAddress(patientDetails.getAddress());
        patient.setPassword(patientDetails.getPassword());
        Patient updatedPatient = patientRepository.save(patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @Operation(summary = "Elimina un paciente por id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Inicia sesión con email y contraseña")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Patient loginRequest) {
        Optional<Patient> optionalPatient = Optional.ofNullable(patientRepository.findByEmail(loginRequest.getEmail()));
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            if (patient.getPassword().equals(loginRequest.getPassword())) {
                Map<String, Object> response = new HashMap<>();
                response.put("token", "some-generated-token"); // Aquí deberías generar y devolver un token real
                response.put("userId", patient.getId());
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(401).body(Map.of("message", "Correo o contraseña incorrectos"));
    }

}
