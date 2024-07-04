package com.tryout_dc.main.usecase;

import com.tryout_dc.main.dto.RegisterResponseDTO;
import com.tryout_dc.main.models.EmployeesModel;
import com.tryout_dc.main.models.JobsModel;
import com.tryout_dc.main.repository.IEmployeesRepository;
import com.tryout_dc.main.repository.IJobsRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class RegisterEmployeeUseCase {

    @Autowired
    IEmployeesRepository employeesRepository;

    @Autowired
    IJobsRepository jobsRepository;

    public RegisterResponseDTO execute(EmployeesModel employee) {

        RegisterResponseDTO response = new RegisterResponseDTO();

        Optional<EmployeesModel> existingEmployee = employeesRepository.findByNameAndLastName(employee.getName(), employee.getLastName());
        if (existingEmployee.isPresent()) {
            response.setSuccess(false);
            response.setMessage("El usuario ingresado ya cuenta con un registro existente");
            return response;
        }

        LocalDate birthDate = LocalDate.parse(employee.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < 18) {
            response.setSuccess(false);
            response.setMessage("El usuario ingresado no cuenta con la mayoria de edad");
            return response;
        }


        Optional<JobsModel> job = jobsRepository.findById(employee.getJobId());
        if (job.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("El puesto ingresado no cuenta con un registro existente");
            return response;
        }

        EmployeesModel registerEmployee = this.employeesRepository.save(employee);
        response.setId(registerEmployee.getEmployeeId());
        response.setSuccess(true);

        return response;
    }

}
