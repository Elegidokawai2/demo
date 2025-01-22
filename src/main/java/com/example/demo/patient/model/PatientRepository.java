package com.example.demo.patient.model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    ///Querys Patientalizadas haciendo uso de JPA

    @Query("SELECT p FROM Patient p")
    List<Patient> findAllPeople();

    @Query("SELECT p FROM Patient p WHERE p.status = true")
    List<Patient> findActivePeople();

    @Query("SELECT p FROM Patient p WHERE p.status = false")
    List<Patient> findInactivePeople();

    @Query("SELECT p FROM Patient p WHERE p.id = :id")
    Optional<Patient> findPatientById(@Param("id") Long id);

    @Query("SELECT p FROM Patient p WHERE p.name LIKE %:name%")
    Optional<Patient> findPatientByName(@Param("name") String name);

    @Query("SELECT p FROM Patient p WHERE p.curp LIKE :curp")
    Optional<Patient> findPatientByCurp(@Param("curp") String curp);

    @Query("SELECT p FROM Patient p WHERE p.phone LIKE :phone")
    Optional<Patient> findPatientByPhone(@Param("phone") String phone);

    @Query(value = "SELECT * FROM patients", nativeQuery = true)
    List<Patient> findAllPeopleNative();

    @Query(value = "SELECT * FROM patients WHERE status = TRUE", nativeQuery = true)
    List<Patient> findActivePeopleIsActiveNative();

    @Query(value = "SELECT * FROM patients WHERE id = ?1", nativeQuery = true)
    Optional<Patient> findPatientByIdNative(Long id);
}