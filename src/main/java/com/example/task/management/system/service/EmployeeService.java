package com.example.task.management.system.service;

import com.example.task.management.system.model.*;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface EmployeeService {
    EmployeeEntity createEmployee(SignUpRequest signUpRequest, String password);
    EmployeeResponse updateEmployee(EmployeeUpdateRequest employeeUpdateRequest);

    void updatePassword(String newPassword);

    void deleteEmployee();
    EmployeeResponse updateRole(Long employeeId, RoleUpdateRequest roleUpdateRequest);
    EmployeeEntity getCurrentEmployee();
    String getCurrentEmployeePassword();
    UserDetailsService userDetailsService();

    EmployeeResponse findEmployeeById(Long employeeId);
}
