package com.example.demo.patient.model;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PatientDTO {
    @NotNull(groups = {Register.class, Modify.class}, message = "El nombre no puede ser nulo")
    private String name;

    @NotNull(groups = {Register.class, Modify.class}, message = "El apellido no puede ser nulo")
    private String lastname;

    @NotNull(groups = {Register.class, Modify.class}, message = "El curp no puede ser nulo")
    private String curp;

    @NotNull(groups = {Register.class, Modify.class}, message = "El telefono no puede ser nulo")
    private long phone;

    @NotNull(groups = {Register.class, Modify.class}, message = "El password no puede ser nulo")
    private int age;

    @NotNull(groups = {Register.class, Modify.class}, message = "Las alergias no pueden ser nulas")
    private List<Long> allergies;

    public interface Register {}
    public interface Modify {}
    public interface ChangeStatus {}
}