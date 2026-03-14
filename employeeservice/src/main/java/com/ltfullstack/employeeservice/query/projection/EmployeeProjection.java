package com.ltfullstack.employeeservice.query.projection;

import com.ltfullstack.commonservice.queries.GetDetailEmployeeQuery;
import com.ltfullstack.employeeservice.command.data.Employee;
import com.ltfullstack.employeeservice.command.data.EmployeeRepository;
import com.ltfullstack.employeeservice.query.model.EmployeeResponseModel;
import com.ltfullstack.employeeservice.query.quries.GetAllEmployeeQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeProjection {

    @Autowired
    private EmployeeRepository employeeRepository;

    @QueryHandler
    public List<EmployeeResponseModel> handle(GetAllEmployeeQuery query) {
        List<Employee> employees = employeeRepository.findAllByIsDisciplined(query.getIsDisciplined());

        return employees.stream().map(employee -> {
            EmployeeResponseModel responseModel = new EmployeeResponseModel();
            BeanUtils.copyProperties(employee, responseModel);
            return responseModel;
        }).toList();
    }

    @QueryHandler
    public EmployeeResponseModel handle(GetDetailEmployeeQuery query) throws Exception {
        Employee employee = employeeRepository.findById(query.getId())
                .orElseThrow(() -> new Exception("Employee not found"));

        EmployeeResponseModel responseModel = new EmployeeResponseModel();
        BeanUtils.copyProperties(employee, responseModel);
        return responseModel;
    }
}
