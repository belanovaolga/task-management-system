package com.example.task.management.system.mapper;

import com.example.task.management.system.model.EmployeeEntity;
import com_example_task_management_system_model.EmployeeResponse;
import com_example_task_management_system_model.SignUpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeMapper {
    public EmployeeEntity fromEmployeeSingUp(SignUpRequest signUpRequest) {
        return EmployeeEntity.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .build();
    }

    public EmployeeResponse toEmployeeResponse(EmployeeEntity employeeEntity) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(employeeEntity.getId());
        employeeResponse.setUsername(employeeEntity.getUsername());
        employeeResponse.setEmail(employeeEntity.getEmail());
        employeeResponse.setRole(employeeEntity.getRole());

        return employeeResponse;
    }

    protected List<EmployeeResponse> toEmployeeResponseList(List<EmployeeEntity> employeeEntityList) {
        return employeeEntityList.stream()
                .map(this::toEmployeeResponse).toList();
    }
}
