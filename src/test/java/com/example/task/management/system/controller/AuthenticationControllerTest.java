package com.example.task.management.system.controller;

import com.example.task.management.system.config.JwtAuthenticationFilter;
import com.example.task.management.system.model.*;
import com.example.task.management.system.service.AuthenticationService;
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
class AuthenticationControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private AuthenticationService mockAuthenticationService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private JwtService jwtService;
    private final EmployeeResponse employeeResponse1;
    private final PasswordUpdateRequest passwordUpdateRequest;
    private final SignInRequest signInRequest;
    private final SignUpRequest signUpRequest;
    private String token;

    public AuthenticationControllerTest() {
        employeeResponse1 = new EmployeeResponse();
        employeeResponse1.setId(1L);
        employeeResponse1.setUsername("user");
        employeeResponse1.setEmail("user@mail.ru");
        employeeResponse1.setRole(RoleEnum.USER);

        signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("user");
        signUpRequest.setEmail("user@mail.ru");

        signInRequest = new SignInRequest();
        signInRequest.setEmail("user@mail.ru");
        signInRequest.setPassword("user");

        passwordUpdateRequest = new PasswordUpdateRequest();
        passwordUpdateRequest.setOldPassword("user");
        passwordUpdateRequest.setNewPassword("newuser");
    }

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AuthenticationController(mockAuthenticationService))
                .addFilters(jwtAuthenticationFilter)
                .build();

        EmployeeEntity employee = EmployeeEntity.builder().id(1L).username("user").email("user@mail.ru").role(RoleEnum.ADMIN).password("user").build();
        this.token = jwtService.generateToken(employee);
    }

    @Test
    void signUp() throws Exception {
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(token);
        Mockito.when(mockAuthenticationService.signUp(signUpRequest)).thenReturn(jwtAuthenticationResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void signUp_whenEmployeeAlreadyExist() throws Exception {
        ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatusCode.valueOf(409), "The user already exists");
        Mockito.when(mockAuthenticationService.signUp(signUpRequest)).thenThrow(responseStatusException);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().is(409));
    }

    @Test
    void signIn() throws Exception {
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(token);
        Mockito.when(mockAuthenticationService.signIn(signInRequest)).thenReturn(jwtAuthenticationResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void signIn_whenPasswordOrEmailIncorrect() throws Exception {
        ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatusCode.valueOf(400), "The email or password is incorrect");
        Mockito.when(mockAuthenticationService.signIn(signInRequest)).thenThrow(responseStatusException);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().is(400));
    }

    @Test
    void updatePassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/auth/update-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(passwordUpdateRequest)))
                .andExpect(status().isNoContent());
    }
}
