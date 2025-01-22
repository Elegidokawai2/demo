package com.example.demo.patient.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",columnDefinition = "VARCHAR(30)")
    private String name;

    @Column(name = "last_name",columnDefinition = "VARCHAR(30)")
    private String last_name;

    @Column(name = "curp",columnDefinition = "VARCHAR(18)")
    private String curp;

    @Column(name = "phone",columnDefinition = "VARCHAR(15)")
    private String phone;

    @Column(name = "age",columnDefinition = "INT")
    private int age;

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Allergies> allergies;

    @Column(name = "status",columnDefinition = "BOOL DEFAULT TRUE")
    private boolean status;

    public Patient() {
    }

    public Patient(Long id, String name, String last_name, String curp, String phone, int age, List<Allergies> allergies, boolean status) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.curp = curp;
        this.phone = phone;
        this.age = age;
        this.allergies = allergies;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Allergies> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Allergies> allergies) {
        this.allergies = allergies;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

