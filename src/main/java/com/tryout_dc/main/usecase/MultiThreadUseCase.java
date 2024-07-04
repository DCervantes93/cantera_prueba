package com.tryout_dc.main.usecase;

import com.tryout_dc.main.models.EmployeesModel;
import com.tryout_dc.main.repository.IEmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class MultiThreadUseCase {

    @Autowired
    IEmployeesRepository employeesRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public ResponseEntity<Map<String, Object>> execute(Map<String, Object> request) {
        List<Long> employeeIds = (List<Long>) request.get("employee_id");
        String startDate = (String) request.get("start_date");
        String endDate = (String) request.get("end_date");

        List<EmployeesModel> employees = fetchEmployees(employeeIds, startDate, endDate);

        if (employees != null && !employees.isEmpty()) {
            List<Map<String, Object>> formattedEmployees = formatEmployees(employees);
            Map<String, Object> response = new HashMap<>();
            response.put("employees", formattedEmployees);
            response.put("success", true);
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("employees", new ArrayList<>());
            response.put("success", false);
            return ResponseEntity.ok(response);
        }
    }

    public List<EmployeesModel> fetchEmployees(List<Long> employeeIds, String startDate, String endDate) {
        LocalDate startLocalDate = LocalDate.parse(startDate);
        LocalDate endLocalDate = LocalDate.parse(endDate);

        List<EmployeesModel> employees = employeesRepository.findAllByEmployeeIdIn(employeeIds);

        employees = employees.stream()
                .filter(employee -> {
                    LocalDate employeeBirthDate = LocalDate.parse(employee.getBirthDate());
                    return !employeeBirthDate.isBefore(startLocalDate) && !employeeBirthDate.isAfter(endLocalDate);
                })
                .collect(Collectors.toList());

        return employees;
    }

    private List<Map<String, Object>> formatEmployees(List<EmployeesModel> employees) {
        List<Map<String, Object>> formattedEmployees = new ArrayList<>();
        for (EmployeesModel employee : employees) {
            Map<String, Object> formattedEmployee = new HashMap<>();
            formattedEmployee.put("gender_id", employee.getGenderId());
            formattedEmployee.put("job_id", employee.getJobId());
            formattedEmployee.put("name", employee.getName());
            formattedEmployee.put("last_name", employee.getLastName());
            formattedEmployee.put("birthdate", employee.getBirthDate());
            formattedEmployees.add(formattedEmployee);
        }
        return formattedEmployees;
    }

    public void shutdown() {
        executorService.shutdown();
    }

}
