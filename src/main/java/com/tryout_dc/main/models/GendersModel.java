package com.tryout_dc.main.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "genders")
public class GendersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "ID")
    private Long genderId;

    @Column(name = "EMPLOYEE_ID")
    @Getter
    @Setter
    private String genderName;

}
