package com.example.demo.patient.model;

import jakarta.persistence.*;

@Entity
public class Allergies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Diseases diseases;

    @ManyToOne
    private Patient patient;

}
