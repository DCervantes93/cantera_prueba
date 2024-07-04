package com.tryout_dc.main.usecase;

import com.tryout_dc.main.dto.WorkedHoursRequestDTO;
import com.tryout_dc.main.dto.WorkedHoursResponseDTO;
import com.tryout_dc.main.models.EmployeesModel;
import com.tryout_dc.main.models.EmployeesWorkedHoursModel;
import com.tryout_dc.main.repository.IEmployeeWorkedHoursModel;
import com.tryout_dc.main.repository.IEmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WorkedHoursUseCase {

    @Autowired
    IEmployeesRepository employeesRepository;
    @Autowired
    IEmployeeWorkedHoursModel workedHoursModel;

    public WorkedHoursResponseDTO execute(WorkedHoursRequestDTO request) throws ParseException {

        WorkedHoursResponseDTO response = new WorkedHoursResponseDTO();

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

            int workedHours = 0;

            List<EmployeesWorkedHoursModel> getHours = this.workedHoursModel.findByEmployeeIdAndWorkDateBetween(request.getEmployeeId(), startDate, endDate);
            for(EmployeesWorkedHoursModel element : getHours){
                if(element.getWorkedHours() != null){
                    workedHours = workedHours+element.getWorkedHours();
                }
            }

            response.setSuccess(true);
            response.setTotalWorkedHours(workedHours);

        }else{
            response.setSuccess(false);
            response.setMessage("El ID de empleado no cuenta con un registro existente");
        }
        return response;

    }

}
