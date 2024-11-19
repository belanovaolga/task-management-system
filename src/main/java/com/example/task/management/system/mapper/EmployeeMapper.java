package com.example.task.management.system.mapper;

import com.example.task.management.system.model.EmployeeEntity;
import com.example.task.management.system.model.request.SignUpRequest;
import com.example.task.management.system.model.response.EmployeeResponse;
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
        return EmployeeResponse.builder()
                .id(employeeEntity.getId())
                .username(employeeEntity.getUsername())
                .email(employeeEntity.getEmail())
                .role(employeeEntity.getRole().toString())
                .build();
    }

    protected List<EmployeeResponse> toEmployeeResponseList(List<EmployeeEntity> employeeEntityList) {
        return employeeEntityList.stream()
                .map(this::toEmployeeResponse).toList();
    }
}
