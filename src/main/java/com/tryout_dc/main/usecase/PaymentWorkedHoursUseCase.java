package com.tryout_dc.main.usecase;

import com.tryout_dc.main.dto.PaymentWorkedHoursDTO;
import com.tryout_dc.main.dto.WorkedHoursRequestDTO;
import com.tryout_dc.main.dto.WorkedHoursResponseDTO;
import com.tryout_dc.main.models.EmployeesModel;
import com.tryout_dc.main.models.EmployeesWorkedHoursModel;
import com.tryout_dc.main.models.JobsModel;
import com.tryout_dc.main.repository.IEmployeeWorkedHoursModel;
import com.tryout_dc.main.repository.IEmployeesRepository;
import com.tryout_dc.main.repository.IJobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentWorkedHoursUseCase {

    @Autowired
    IEmployeesRepository employeesRepository;
    @Autowired
    IEmployeeWorkedHoursModel workedHoursModel;
    @Autowired
    IJobsRepository jobsRepository;

    public PaymentWorkedHoursDTO execute(WorkedHoursRequestDTO request) throws ParseException {

        PaymentWorkedHoursDTO response = new PaymentWorkedHoursDTO();

        Optional<EmployeesModel> searchEmployee = this.employeesRepository.findById(request.getEmployeeId());
        if(searchEmployee.isPresent()){

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(request.getStartDate());
            Date endDate = dateFormat.parse(request.getEndDate());

            if(startDate.after(endDate)){
                response.setSuccess(false);
                response.setMessage("La Fecha de inicio ingresada es mayor que la fecha final ingresada");

                return response;
            }


            Optional<JobsModel> getJobInfo = this.jobsRepository.findById(searchEmployee.get().getEmployeeId());
            if(getJobInfo.isEmpty()){
                response.setSuccess(false);
                response.setMessage("El puesto asignado al empleado no se encuentra registrado en la base de datos");

                return response;
            }

            int salaryValue = getJobInfo.get().getSalary();
            int workedHours = 0;
            List<EmployeesWorkedHoursModel> getHours = this.workedHoursModel.findByEmployeeIdAndWorkDateBetween(request.getEmployeeId(), startDate, endDate);
            for(EmployeesWorkedHoursModel element : getHours){
                if(element.getWorkedHours() != null){
                    workedHours = workedHours+element.getWorkedHours();
                }
            }

            int paymentValue = salaryValue*workedHours;
            response.setSuccess(true);
            response.setPayment(paymentValue);

        }else{
            response.setSuccess(false);
            response.setMessage("El ID de empleado no cuenta con un registro existente");
        }
        return response;

    }

}
