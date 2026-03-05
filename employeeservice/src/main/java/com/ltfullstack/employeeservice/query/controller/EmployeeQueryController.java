package com.ltfullstack.employeeservice.query.controller;

import com.ltfullstack.employeeservice.query.model.EmployeeResponseModel;
import com.ltfullstack.employeeservice.query.quries.GetAllEmployeeQuery;
import com.ltfullstack.employeeservice.query.quries.GetDetailEmployeeQuery;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employee Query")
@Hidden
public class EmployeeQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @Operation(
            summary = "Get List Employees",
            description = "Get List Employees",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping
    public List<EmployeeResponseModel> getAllEmployees(@RequestParam(required = false, defaultValue = "false") Boolean isDisciplined) {
        log.info("Get all Employees");
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
