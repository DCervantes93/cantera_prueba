package com.tryout_dc.main.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "jobs")
public class JobsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "ID")
    private Long jobId;

    @Column(name = "NAME")
    @Getter
    @Setter
    private String jobName;

    @Column(name = "SALARY")
    @Getter
    @Setter
    private Integer salary;


}
