package com.example.task.management.system.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "Ответ с информацией о сущности")
public class EmployeeResponse {
    private Long id;
    private String username;
    private String email;
    private String role;
}
