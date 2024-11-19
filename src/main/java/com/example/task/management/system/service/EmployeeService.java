package com.example.task.management.system.service;

import com.example.task.management.system.model.EmployeeEntity;
import com.example.task.management.system.model.request.EmployeeUpdateRequest;
import com.example.task.management.system.model.request.RoleUpdateRequest;
import com.example.task.management.system.model.request.SignUpRequest;
import com.example.task.management.system.model.response.EmployeeResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface EmployeeService {
    EmployeeEntity createEmployee(SignUpRequest signUpRequest, String password);
    EmployeeResponse findEmployeeById(Long employeeId);
    EmployeeResponse updateEmployee(EmployeeUpdateRequest employeeUpdateRequest);
    EmployeeResponse updatePassword(String newPassword);
    EmployeeResponse deleteEmployee();
    EmployeeResponse updateRole(Long employeeId, RoleUpdateRequest roleUpdateRequest);
    EmployeeEntity getCurrentEmployee();
    String getCurrentEmployeePassword();
    UserDetailsService userDetailsService();
}
