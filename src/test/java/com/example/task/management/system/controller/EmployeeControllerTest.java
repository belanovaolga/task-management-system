package com.example.task.management.system.controller;

import com.example.task.management.system.config.JwtAuthenticationFilter;
import com.example.task.management.system.model.*;
import com.example.task.management.system.service.EmployeeService;
import com.example.task.management.system.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ObjectMapper.class, JwtAuthenticationFilter.class, JwtService.class})
@AutoConfigureMockMvc
class EmployeeControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService mockEmployeeService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private JwtService jwtService;
    private final EmployeeResponse employeeResponse1;
    private final EmployeeResponse employeeResponse2;
    private final EmployeeUpdateRequest employeeUpdateRequest;
    private final RoleUpdateRequest roleUpdateRequest;
    private String token;

    public EmployeeControllerTest() {
        this.employeeResponse1 = new EmployeeResponse();
        employeeResponse1.setId(1L);
        employeeResponse1.setUsername("user");
        employeeResponse1.setEmail("user@mail.ru");
        employeeResponse1.setRole(RoleEnum.USER);

        this.employeeResponse2 = new EmployeeResponse();
        employeeResponse2.setId(1L);
        employeeResponse2.setUsername("user");
        employeeResponse2.setEmail("user@mail.ru");
        employeeResponse2.setRole(RoleEnum.ADMIN);

        employeeUpdateRequest = new EmployeeUpdateRequest("user", "user@mail.ru");
        roleUpdateRequest = new RoleUpdateRequest(RoleEnum.ADMIN);
    }

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new EmployeeController(mockEmployeeService))
                .addFilters(jwtAuthenticationFilter)
                .build();

        EmployeeEntity employee = EmployeeEntity.builder().id(1L).username("user").email("user@mail.ru").role(RoleEnum.ADMIN).password("user").build();
        this.token = jwtService.generateToken(employee);
    }

    @Test
    void updateEmployee() throws Exception {
        Mockito.when(this.mockEmployeeService.updateEmployee(employeeUpdateRequest)).thenReturn(employeeResponse1);

        mockMvc.perform(MockMvcRequestBuilders.put("/employees/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(employeeUpdateRequest))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.email").value("user@mail.ru"))
                .andExpect(jsonPath("$.role").value(RoleEnum.USER.toString()));
    }

    @Test
    void updateEmployee_whenEmployeeNotFound() throws Exception {
        ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatusCode.valueOf(404), "The user does not exist");
        Mockito.when(mockEmployeeService.updateEmployee(employeeUpdateRequest)).thenThrow(responseStatusException);

        mockMvc.perform(MockMvcRequestBuilders.put("/employees/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(employeeUpdateRequest))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().is(404));
    }

    @Test
    void updateEmployee_whenEmployeeAlreadyExist() throws Exception {
        ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatusCode.valueOf(409), "The user already exists");
        Mockito.when(mockEmployeeService.updateEmployee(employeeUpdateRequest)).thenThrow(responseStatusException);

        mockMvc.perform(MockMvcRequestBuilders.put("/employees/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(employeeUpdateRequest))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().is(409));
    }

    @Test
    void deleteEmployee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/delete"))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateRole() throws Exception {
        Long employeeId = 1L;
        Mockito.when(mockEmployeeService.updateRole(employeeId, roleUpdateRequest)).thenReturn(employeeResponse2);

        mockMvc.perform(MockMvcRequestBuilders.put("/employees/1/role", employeeId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(roleUpdateRequest))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.email").value("user@mail.ru"))
                .andExpect(jsonPath("$.role").value(RoleEnum.ADMIN.toString()));
    }

    @Test
    void updateRole_whenRoleNotAdmin() throws Exception {
        Long employeeId = 1L;
        ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatusCode.valueOf(403));
        Mockito.when(mockEmployeeService.updateRole(employeeId, roleUpdateRequest)).thenThrow(responseStatusException);

        mockMvc.perform(MockMvcRequestBuilders.put("/employees/1/role", employeeId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(roleUpdateRequest)))
                .andExpect(status().is(403));
    }
}
