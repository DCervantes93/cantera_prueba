package com.tryout_dc.main.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employees")
public class EmployeesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "ID")
    private Long employeeId;

    @Column(name = "GENDER_ID")
    @Getter
    @Setter
    private Long genderId;

    @Getter
    @Setter
    @Column(name = "JOB_ID")
    private Long jobId;

    @Getter
    @Setter
    @Column(name = "NAME")
    private String name;


    @Getter
    @Setter
    @Column(name = "LAST_NAME")
    private String lastName;

    @Getter
    @Setter
    @Column(name = "BIRTHDATE")
    private String birthDate;

}
