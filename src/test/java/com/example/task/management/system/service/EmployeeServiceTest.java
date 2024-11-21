package com.example.task.management.system.service;

import com.example.task.management.system.exception.EmployeeAlreadyExists;
import com.example.task.management.system.exception.EmployeeNotFound;
import com.example.task.management.system.mapper.EmployeeMapper;
import com.example.task.management.system.model.*;
import com.example.task.management.system.model.putAuth.UserDetailsDto;
import com.example.task.management.system.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmployeeServiceTest {
    private final EmployeeService employeeService;
    private final EmployeeRepository mockEmployeeRepository;
    private final AuthService mockAuthService;
    private final UserDetailsDto mockUserDetailsDto;
    private final EmployeeMapper employeeMapper;
    private final EmployeeEntity employee1;
    private final EmployeeEntity employee2;

    public EmployeeServiceTest() {
        mockEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        mockAuthService = Mockito.mock(AuthService.class);
        employeeMapper = new EmployeeMapper();
        employeeService = new EmployeeServiceImpl(mockEmployeeRepository, mockAuthService, employeeMapper);
        mockUserDetailsDto = Mockito.mock(UserDetailsDto.class);

        employee1 = EmployeeEntity.builder().username("james").email("james@mail.ru").password("james").role(RoleEnum.USER).build();
        employee2 = EmployeeEntity.builder().username("jon").email("jon@mail.ru").password("jon").role(RoleEnum.USER).build();
    }

    @Test
    void shouldCreateEmployee() {
        SignUpRequest signUpRequest = new SignUpRequest("james", "james@mail.ru", "james");
        Mockito.when(mockEmployeeRepository.existsByEmail(employee1.getEmail())).thenReturn(false);
        Optional<EmployeeEntity> optionalEmployee = Optional.of(employee1);
        Mockito.when(mockEmployeeRepository.findById(employee1.getId())).thenReturn(optionalEmployee);

        EmployeeEntity actualEmployee = employeeService.createEmployee(signUpRequest, "james");

        assertEquals(employee1, actualEmployee);
    }

    @Test
    void shouldCreateEmployee_whenEmployeeAlreadyExists() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("james@mail.ru");
        Mockito.when(mockEmployeeRepository.existsByEmail(employee1.getEmail())).thenReturn(true);

        assertThrows(EmployeeAlreadyExists.class, () -> {
            employeeService.createEmployee(signUpRequest, "password");
        });
    }

    @Test
    void shouldUpdateEmployee() {
        EmployeeEntity expectedEmployee = new EmployeeEntity();
        expectedEmployee.setUsername("jon");
        expectedEmployee.setEmail("jonjon@mail.ru");
        expectedEmployee.setRole(RoleEnum.USER);
        Mockito.when(mockAuthService.getCurrentEmployeesId()).thenReturn(mockUserDetailsDto);
        Mockito.when(mockUserDetailsDto.getId()).thenReturn(employee2.getId());
        Optional<EmployeeEntity> optionalEmployee = Optional.of(employee2);
        Mockito.when(mockEmployeeRepository.findById(employee2.getId())).thenReturn(optionalEmployee);
        Mockito.when(mockEmployeeRepository.existsByEmail(expectedEmployee.getEmail())).thenReturn(false);
        Mockito.when(mockEmployeeRepository.save(employee2)).thenReturn(employee2);

        EmployeeUpdateRequest employeeUpdDto = new EmployeeUpdateRequest("jon", "jonjon@mail.ru");
        employeeService.updateEmployee(employeeUpdDto);

        Mockito.when(mockEmployeeRepository.findById(employee2.getId())).thenReturn(optionalEmployee);
        EmployeeResponse employeeResponse = employeeService.findEmployeeById(employee2.getId());

        EmployeeEntity actualEmployee = employeeMapper.toEmployeeEntity(employeeResponse);

        assertEquals(expectedEmployee, actualEmployee);
    }

    @Test
    void shouldDeleteEmployee() {
        Mockito.when(mockAuthService.getCurrentEmployeesId()).thenReturn(mockUserDetailsDto);
        Mockito.when(mockUserDetailsDto.getId()).thenReturn(employee1.getId());
        Optional<EmployeeEntity> optionalEmployee = Optional.of(employee1);
        Mockito.when(mockEmployeeRepository.findById(employee1.getId())).thenReturn(optionalEmployee);
        Mockito.doNothing().when(mockEmployeeRepository).delete(employee1);

        employeeService.deleteEmployee();

        Mockito.when(mockEmployeeRepository.findById(employee1.getId())).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFound.class, () -> {
            employeeService.findEmployeeById(employee1.getId());
        });
    }

    @Test
    void shouldUpdateRole() {
        EmployeeResponse expectedEmployee = new EmployeeResponse();
        expectedEmployee.setUsername("jon");
        expectedEmployee.setEmail("jon@mail.ru");
        expectedEmployee.setRole(RoleEnum.ADMIN);
        Optional<EmployeeEntity> optionalEmployee = Optional.ofNullable(employee2);
        assert employee2 != null;
        Mockito.when(mockEmployeeRepository.findById(employee2.getId())).thenReturn(optionalEmployee);
        Mockito.when(mockEmployeeRepository.save(employee2)).thenReturn(employee2);

        RoleUpdateRequest roleUpdateRequest = new RoleUpdateRequest(RoleEnum.ADMIN);
        employeeService.updateRole(employee2.getId(), roleUpdateRequest);

        EmployeeResponse actualEmployee = employeeService.findEmployeeById(employee2.getId());

        assertEquals(expectedEmployee, actualEmployee);
    }

    @Test
    void shouldUpdateRole_whenEmployeeNoyFound() {
        RoleUpdateRequest roleUpdateRequest = new RoleUpdateRequest(RoleEnum.ADMIN);

        Mockito.when(mockEmployeeRepository.findById(employee2.getId())).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFound.class, () -> {
            employeeService.updateRole(employee2.getId(), roleUpdateRequest);
        });
    }
}
