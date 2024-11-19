package com.example.task.management.system.service;

import com.example.task.management.system.exception.EmployeeAlreadyExists;
import com.example.task.management.system.exception.EmployeeNotFound;
import com.example.task.management.system.mapper.EmployeeMapper;
import com.example.task.management.system.model.EmployeeEntity;
import com.example.task.management.system.model.enums.Role;
import com.example.task.management.system.model.putAuth.UserDetailsDto;
import com.example.task.management.system.model.request.EmployeeUpdateRequest;
import com.example.task.management.system.model.request.RoleUpdateRequest;
import com.example.task.management.system.model.request.SignUpRequest;
import com.example.task.management.system.model.response.EmployeeResponse;
import com.example.task.management.system.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AuthService authService;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeEntity createEmployee(SignUpRequest signUpRequest, String password) {
        EmployeeEntity employeeEntity = employeeMapper.fromEmployeeSingUp(signUpRequest);
        employeeEntity.setRole(Role.ADMIN);
        employeeEntity.setPassword(password);


        if (!employeeRepository.existsByEmail(employeeEntity.getEmail())) {
            employeeRepository.save(employeeEntity);

        } else {
            throw new EmployeeAlreadyExists();
        }

        return employeeEntity;
    }

    @Override
    public EmployeeResponse findEmployeeById(Long employeeId) {
        EmployeeEntity employee = findById(employeeId);

        return employeeMapper.toEmployeeResponse(employee);
    }

    @Override
    public EmployeeResponse updateEmployee(EmployeeUpdateRequest employeeUpdateRequest) {
        UserDetailsDto userDetailsDto = authService.getCurrentEmployeesId();
        EmployeeEntity currentEmployee = findById(userDetailsDto.getId());

        currentEmployee.setUsername(employeeUpdateRequest.getUsername());

        if (!employeeRepository.existsByEmail(employeeUpdateRequest.getEmail()) || currentEmployee.getEmail().equals(employeeUpdateRequest.getEmail())) {
            currentEmployee.setEmail(employeeUpdateRequest.getEmail());
        } else {
            throw new EmployeeAlreadyExists();
        }

        EmployeeEntity employeeEntity = employeeRepository.save(currentEmployee);

        return employeeMapper.toEmployeeResponse(employeeEntity);
    }

    @Override
    public EmployeeResponse updatePassword(String newPassword) {
        UserDetailsDto userDetailsDto = authService.getCurrentEmployeesId();
        EmployeeEntity currentEmployee = findById(userDetailsDto.getId());
        currentEmployee.setPassword(newPassword);

        employeeRepository.save(currentEmployee);

        return employeeMapper.toEmployeeResponse(currentEmployee);
    }

    @Override
    public EmployeeResponse deleteEmployee() {
        UserDetailsDto userDetailsDto = authService.getCurrentEmployeesId();
        EmployeeEntity currentEmployee = findById(userDetailsDto.getId());

        employeeRepository.delete(currentEmployee);

        return employeeMapper.toEmployeeResponse(currentEmployee);
    }

    @Override
    public EmployeeResponse updateRole(Long employeeId, RoleUpdateRequest roleUpdateRequest) {
        EmployeeEntity employeeEntity = findById(employeeId);

        if (!employeeEntity.getRole().equals(roleUpdateRequest.getRole())) {
            employeeEntity.setRole(roleUpdateRequest.getRole());
            employeeRepository.save(employeeEntity);
        }

        EmployeeResponse employeeResponse = employeeMapper.toEmployeeResponse(employeeEntity);

        return employeeResponse;
    }

    @Override
    public EmployeeEntity getCurrentEmployee() {
        UserDetailsDto userDetailsDto = authService.getCurrentEmployeesId();
        return findById(userDetailsDto.getId());
    }

    @Override
    public String getCurrentEmployeePassword() {
        EmployeeEntity currentEmployee = getCurrentEmployee();

        return currentEmployee.getPassword();
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::findEmployeeByEmailUDS;
    }

    protected EmployeeEntity findById(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFound::new);
    }

    private EmployeeEntity findEmployeeByEmailUDS(String email) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
}
