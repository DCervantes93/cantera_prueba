package com.tryout_dc.main.usecase;

import com.tryout_dc.main.models.EmployeesModel;
import com.tryout_dc.main.models.JobsModel;
import com.tryout_dc.main.repository.IEmployeesRepository;
import com.tryout_dc.main.repository.IJobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeesByJobUseCase {

    @Autowired
    IEmployeesRepository employeesRepository;

    @Autowired
    IJobsRepository jobsRepository;

    public Map<String, Object> execute(Long jobId) {
        Map<String, Object> response = new HashMap<>();
        try {

            List<EmployeesModel> employeesByJob = employeesRepository.findByJobId(jobId);

            List<EmployeesModel> filteredEmployees = employeesByJob.stream()
                    .filter(employee -> employee.getJobId().equals(jobId))
                    .collect(Collectors.toList());

            List<EmployeesModel> sortedEmployees = filteredEmployees.stream()
                    .sorted(Comparator.comparing(EmployeesModel::getLastName))
                    .collect(Collectors.toList());

            Map<String, List<EmployeesModel>> groupedByLastName = sortedEmployees.stream()
                    .collect(Collectors.groupingBy(EmployeesModel::getLastName));

            List<Map<String, Object>> formattedEmployees = groupedByLastName.entrySet().stream()
                    .flatMap(entry -> entry.getValue().stream()
                            .map(employee -> {
                                Map<String, Object> employeeInfo = new HashMap<>();
                                employeeInfo.put("id", employee.getEmployeeId());
                                employeeInfo.put("name", employee.getName());
                                employeeInfo.put("last_name", employee.getLastName());
                                employeeInfo.put("birthdate", employee.getBirthDate());

                                JobsModel job = jobsRepository.findById(employee.getJobId()).orElse(null);
                                Map<String, Object> jobInfo = new HashMap<>();
                                if (job != null) {
                                    jobInfo.put("id", job.getJobId());
                                    jobInfo.put("name", job.getJobName());
                                    jobInfo.put("salary", job.getSalary());
                                }
                                employeeInfo.put("job", jobInfo);
                                return employeeInfo;
                            }))
                    .collect(Collectors.toList());

            response.put("employees", formattedEmployees);
            response.put("success", true);
        } catch (Exception e) {
            response.put("employees", null);
            response.put("success", false);
        }
        return response;
    }

}
