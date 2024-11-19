package com.example.task.management.system.model.request;


import com.example.task.management.system.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на изменение роли пользователя")
public class RoleUpdateRequest {
    @NotNull
    private Role role;
}
