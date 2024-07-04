package com.tryout_dc.main.repository;

import com.tryout_dc.main.models.EmployeesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IEmployeesRepository extends JpaRepository<EmployeesModel, Long> {

    Optional<EmployeesModel> findByNameAndLastName(String name, String lastName);
    List<EmployeesModel> findByJobId(Long jobId);
    List<EmployeesModel> findAllByEmployeeIdIn(List<Long> employeeIds);

}
