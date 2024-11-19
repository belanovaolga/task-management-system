package com.example.task.management.system.controller;

import com.example.task.management.system.service.EmployeeService;
import com_example_task_management_system_api.EmployeesApi;
import com_example_task_management_system_model.EmployeeResponse;
import com_example_task_management_system_model.EmployeeUpdateRequest;
import com_example_task_management_system_model.RoleUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeesApi {
    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<EmployeeResponse> updateEmployee(EmployeeUpdateRequest employeeUpdateRequest) {
        employeeService.updateEmployee(employeeUpdateRequest);
        return EmployeesApi.super.updateEmployee(employeeUpdateRequest);
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
