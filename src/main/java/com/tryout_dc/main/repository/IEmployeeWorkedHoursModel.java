package com.tryout_dc.main.repository;

import com.tryout_dc.main.models.EmployeesWorkedHoursModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface IEmployeeWorkedHoursModel extends JpaRepository<EmployeesWorkedHoursModel, Long> {

    List<EmployeesWorkedHoursModel> findByEmployeeIdAndWorkDateBetween(Long employeeId, Date startDate, Date endDate);

}
