package com.example.task.management.system.controller;

import com.example.task.management.system.exception.EmployeeNotFound;
import com.example.task.management.system.model.request.EmployeeUpdateRequest;
import com.example.task.management.system.model.request.RoleUpdateRequest;
import com.example.task.management.system.model.response.EmployeeResponse;
import com.example.task.management.system.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
@Tag(name = "Контроллер пользователя", description = "Отвечает за все, что связано с аккаунтами пользователей")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PutMapping
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Изменение пользователя",
            description = "Позволяет пользователю изменить информацию о себе"
    )
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @RequestBody EmployeeUpdateRequest employeeUpdateRequest
    ) throws EmployeeNotFound {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeUpdateRequest));
    }

    @DeleteMapping
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удаление пользователя",
            description = "Позволяет пользователю удалить его аккаунт"
    )
    public void deleteEmployee(
    ) throws EmployeeNotFound {
        ResponseEntity.ok(employeeService.deleteEmployee());
    }

    @PutMapping("/{employeeId}/role")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Изменение роли",
            description = "Позволяет пользователю изменить роль любого пользователя"
    )
    public ResponseEntity<EmployeeResponse> updateRole(
            @PathVariable(name = "employeeId") Long employeeId,
            @RequestBody RoleUpdateRequest roleUpdateRequest
    ) throws EmployeeNotFound {
        return ResponseEntity.ok(employeeService.updateRole(employeeId, roleUpdateRequest));
    }
}
