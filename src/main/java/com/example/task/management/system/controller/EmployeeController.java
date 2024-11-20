package com.example.task.management.system.controller;

import com.example.task.management.system.api.EmployeesApi;
import com.example.task.management.system.model.EmployeeResponse;
import com.example.task.management.system.model.EmployeeUpdateRequest;
import com.example.task.management.system.model.RoleUpdateRequest;
import com.example.task.management.system.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeesApi {
    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<EmployeeResponse> updateEmployee(EmployeeUpdateRequest employeeUpdateRequest) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeUpdateRequest));
    }

    @Override
    public ResponseEntity<Void> deleteEmployee() {
        employeeService.deleteEmployee();
        return EmployeesApi.super.deleteEmployee();
    }

    @Override
    public ResponseEntity<EmployeeResponse> updateRole(Long employeeId, RoleUpdateRequest roleUpdateRequest) {
        employeeService.updateRole(employeeId, roleUpdateRequest);
        return EmployeesApi.super.updateRole(employeeId, roleUpdateRequest);
    }
}
