package com.tryout_dc.main.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "employees_worked_hours")
public class EmployeesWorkedHoursModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "ID")
    private Long WorkedHourId;

    @Column(name = "EMPLOYEE_ID")
    @Getter
    @Setter
    private Long employeeId;

    @Getter
    @Setter
    @Column(name = "WORKED_HOURS")
    private Integer workedHours;

    @Getter
    @Setter
    @Column(name = "WORK_DATE")
    private Date workDate;
}
