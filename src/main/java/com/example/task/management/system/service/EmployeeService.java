package com.example.task.management.system.service;

import com.example.task.management.system.model.EmployeeEntity;
import com_example_task_management_system_model.EmployeeResponse;
import com_example_task_management_system_model.EmployeeUpdateRequest;
import com_example_task_management_system_model.RoleUpdateRequest;
import com_example_task_management_system_model.SignUpRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface EmployeeService {
    EmployeeEntity createEmployee(SignUpRequest signUpRequest, String password);
    EmployeeResponse updateEmployee(EmployeeUpdateRequest employeeUpdateRequest);
    EmployeeResponse updatePassword(String newPassword);
    EmployeeResponse deleteEmployee();
    EmployeeResponse updateRole(Long employeeId, RoleUpdateRequest roleUpdateRequest);
    EmployeeEntity getCurrentEmployee();
    String getCurrentEmployeePassword();
    UserDetailsService userDetailsService();
}
