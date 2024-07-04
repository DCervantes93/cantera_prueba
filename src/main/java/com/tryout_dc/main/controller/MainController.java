package com.tryout_dc.main.controller;

import com.tryout_dc.main.dto.PaymentWorkedHoursDTO;
import com.tryout_dc.main.dto.RegisterResponseDTO;
import com.tryout_dc.main.dto.WorkedHoursRequestDTO;
import com.tryout_dc.main.dto.WorkedHoursResponseDTO;
import com.tryout_dc.main.models.EmployeesModel;
import com.tryout_dc.main.usecase.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/main")
public class MainController {


    @Autowired
    RegisterEmployeeUseCase registerEmployeeUseCase;
    @Autowired
    EmployeesByJobUseCase employeesByJobUseCase;
    @Autowired
    MultiThreadUseCase multiThreadUseCase;
    @Autowired
    WorkedHoursUseCase workedHoursUseCase;
    @Autowired
    PaymentWorkedHoursUseCase paymentWorkedHoursUseCase;

    @PostMapping("/add-employee")
    public RegisterResponseDTO addEmployee(@RequestBody EmployeesModel employee) {

        RegisterResponseDTO response = new RegisterResponseDTO();

        try {
            response = registerEmployeeUseCase.execute(employee);
        }catch(Exception e){
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @GetMapping("/list-by-job")
    public Map<String, Object> listEmployeesByJob(@RequestParam Long jobId) {
        return employeesByJobUseCase.execute(jobId);
    }

    @PostMapping("/get-employees")
    public ResponseEntity<Map<String, Object>> getEmployees(@RequestBody Map<String, Object> request) {
        return multiThreadUseCase.execute(request);
    }

    @PostMapping("/total-worked-hours")
    public WorkedHoursResponseDTO getWorkedHours(@RequestBody WorkedHoursRequestDTO request) throws ParseException {
        return workedHoursUseCase.execute(request);
    }

    @PostMapping("/payment-work")
    public PaymentWorkedHoursDTO getPaymentWork(@RequestBody WorkedHoursRequestDTO request) throws ParseException {
        return paymentWorkedHoursUseCase.execute(request);
    }

}
