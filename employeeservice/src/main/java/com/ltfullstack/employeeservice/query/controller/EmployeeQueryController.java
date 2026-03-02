package com.ltfullstack.employeeservice.query.controller;

import com.ltfullstack.employeeservice.query.model.EmployeeResponseModel;
import com.ltfullstack.employeeservice.query.quries.GetAllEmployeeQuery;
import com.ltfullstack.employeeservice.query.quries.GetDetailEmployeeQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<EmployeeResponseModel> getAllEmployees(@RequestParam(required = false, defaultValue = "false") Boolean isDisciplined) {
        return queryGateway.query(
                new GetAllEmployeeQuery(isDisciplined),
                ResponseTypes.multipleInstancesOf(EmployeeResponseModel.class)
        ).join();
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponseModel getEmployee(@PathVariable String employeeId) {
        return queryGateway.query(
                new GetDetailEmployeeQuery(employeeId),
                ResponseTypes.instanceOf(EmployeeResponseModel.class)
        ).join();
    }
}
