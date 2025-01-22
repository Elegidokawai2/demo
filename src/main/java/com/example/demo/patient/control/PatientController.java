package com.example.demo.patient.control;
import com.example.demo.patient.model.Patient;
import com.example.demo.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService PatientService;

    @Autowired
    public PatientController(PatientService PatientService) {
        this.PatientService = PatientService;
    }

    @GetMapping("/all")
    public ResponseEntity<Message> getAllPeople() {
        return PatientService.findAll();
    }

    @GetMapping("/allActive")
    public ResponseEntity<Message> getAllActivePeople() {
        return PatientService.findAllActive();
    }

    @GetMapping("/allInactive")
    public ResponseEntity<Message> getAllInactivePeople() {
        return PatientService.findAllInactive();
    }

    @PostMapping("/save")
    public ResponseEntity<Message> savePatient(@RequestBody Patient Patient) {
        return PatientService.save(Patient);
    }

    @PutMapping("/update")
    public ResponseEntity<Message> updatePatient(@RequestBody Patient Patient) {
        return PatientService.update(Patient);
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<Message> changeStatus(@RequestBody Patient Patient) {
        return PatientService.changeStatus(Patient);
    }
}