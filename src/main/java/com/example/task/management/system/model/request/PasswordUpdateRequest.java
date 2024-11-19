package com.example.task.management.system.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на изменение пароля")
public class PasswordUpdateRequest {
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
