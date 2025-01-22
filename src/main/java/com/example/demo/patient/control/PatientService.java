package com.example.demo.patient.control;

import com.example.demo.patient.model.Patient;
import com.example.demo.patient.model.PatientRepository;
import com.example.demo.utils.Message;
import com.example.demo.utils.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository PatientRepository;
    private Patient lastRegisteredPatient;

    @Autowired
    public PatientService(PatientRepository PatientRepository) {
        this.PatientRepository = PatientRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<Patient> people = PatientRepository.findAll();
        logger.info("La búsqueda ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(people,"Listado de Patientas", TypesResponse.SUCCESS), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAllActive() {
        List<Patient> people = PatientRepository.findActivePeople();
        logger.info("La búsqueda ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(people,"Listado de Patientas activas", TypesResponse.SUCCESS), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAllInactive() {
        List<Patient> people = PatientRepository.findInactivePeople();
        logger.info("La búsqueda ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(people,"Listado de Patientas inactivas", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> save(Patient Patient) {
        if(Patient.getName().length() > 30) {
            return new ResponseEntity<>(new Message("El nombre excede el número de caracteres",TypesResponse.WARNING),HttpStatus.BAD_REQUEST);
        }
        if(Patient.getLast_name().length() > 30) {
            return new ResponseEntity<>(new Message("Los apellidos exceden el número de caracteres",TypesResponse.WARNING),HttpStatus.BAD_REQUEST);
        }
        if(Patient.getCurp().length() > 18 || !PatientRepository.findPatientByCurp(Patient.getCurp()).isEmpty()) {
            return new ResponseEntity<>(new Message("El CURP excede el número de caracteres o ya esta registrado",TypesResponse.WARNING),HttpStatus.BAD_REQUEST);
        }
        if(Patient.getPhone().length() > 12 || !PatientRepository.findPatientByPhone(Patient.getPhone()).isEmpty()) {
            return new ResponseEntity<>(new Message("El telefono excede el número de caracteres o ya esta registrado",TypesResponse.WARNING),HttpStatus.BAD_REQUEST);
        }
        Patient = PatientRepository.saveAndFlush(Patient);
        if(Patient == null){
            return new ResponseEntity<>(new Message("La Patienta no se registró",TypesResponse.ERROR),HttpStatus.BAD_REQUEST);
        }

        lastRegisteredPatient = Patient;
        logger.info("El registro ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(Patient,"La Patienta se registró correctamente",TypesResponse.SUCCESS),HttpStatus.CREATED);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> update(Patient Patient) {
        Optional<Patient> PatientOptional = PatientRepository.findById(Patient.getId());
        if(!PatientOptional.isPresent()){
            return new ResponseEntity<>(new Message("La Patienta no existe",TypesResponse.ERROR),HttpStatus.NOT_FOUND);
        }

        Patient PatientUpdated = PatientOptional.get();
        if(Patient.getName().length() > 30) {
            return new ResponseEntity<>(new Message("El nombre excede el número de caracteres",TypesResponse.WARNING),HttpStatus.BAD_REQUEST);
        }
        if(Patient.getLast_name().length() > 30) {
            return new ResponseEntity<>(new Message("Los apellidos exceden el número de caracteres",TypesResponse.WARNING),HttpStatus.BAD_REQUEST);
        }
        if(Patient.getCurp().length() > 18) {
            return new ResponseEntity<>(new Message("El CURP excede el número de caracteres",TypesResponse.WARNING),HttpStatus.BAD_REQUEST);
        }
        if(Patient.getPhone().length() > 12) {
            return new ResponseEntity<>(new Message("El telefono excede el número de caracteres",TypesResponse.WARNING),HttpStatus.BAD_REQUEST);
        }

        PatientUpdated.setName(Patient.getName());
        PatientUpdated.setLast_name(Patient.getLast_name());
        PatientUpdated.setCurp(Patient.getCurp());
        PatientUpdated.setPhone(Patient.getPhone());
        PatientUpdated.setAllergies(Patient.getAllergies());
        PatientUpdated.setAge(Patient.getAge());

        PatientUpdated = PatientRepository.saveAndFlush(PatientUpdated);
        if(PatientUpdated == null){
            return new ResponseEntity<>(new Message("La Patienta no se actualizó",TypesResponse.ERROR),HttpStatus.BAD_REQUEST);
        }
        logger.info("La actualización ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(Patient,"La Patienta se actualizó correctamente",TypesResponse.SUCCESS),HttpStatus.OK);
    }
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Message> changeStatus(Patient Patient) {
        Optional<Patient> PatientOptional = PatientRepository.findPatientById(Patient.getId());
        if(PatientOptional.isEmpty()){
            return new ResponseEntity<>(new Message("La Patienta no existe",TypesResponse.ERROR),HttpStatus.NOT_FOUND);
        }

        Patient PatientUpdated = PatientOptional.get();
        PatientUpdated.setName(PatientUpdated.getName());
        PatientUpdated.setLast_name(PatientUpdated.getLast_name());
        PatientUpdated.setCurp(PatientUpdated.getCurp());
        PatientUpdated.setPhone(PatientUpdated.getPhone());
        PatientUpdated.setAllergies(PatientUpdated.getAllergies());
        PatientUpdated.setStatus(!PatientUpdated.isStatus());

        PatientUpdated = PatientRepository.saveAndFlush(PatientUpdated);
        if(PatientUpdated == null){
            return new ResponseEntity<>(new Message("La Patienta no se actualizó",TypesResponse.ERROR),HttpStatus.BAD_REQUEST);
        }
        logger.info("La actualización ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(Patient,"La Patienta se actualizó correctamente",TypesResponse.SUCCESS),HttpStatus.OK);
    }
}