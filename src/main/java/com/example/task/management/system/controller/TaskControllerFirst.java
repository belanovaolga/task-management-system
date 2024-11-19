package com.example.task.management.system.controller;

import com.example.task.management.system.exception.TaskNotFound;
import com.example.task.management.system.model.request.ByEmployeeTaskRequest;
import com.example.task.management.system.model.request.NewTaskRequest;
import com.example.task.management.system.model.request.TaskUpdateRequest;
import com.example.task.management.system.model.response.TaskResponse;
import com.example.task.management.system.model.response.TasksListResponse;
import com.example.task.management.system.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Контроллер задач", description = "Отвечает за все, что связано с задачами")
public class TaskControllerFirst {
    private final TaskService taskService;

    @PostMapping
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Создание задачи",
            description = "Позволяет пользователю создать задачу"
    )
    public ResponseEntity<TaskResponse> createTask(
            @RequestBody NewTaskRequest newTaskRequest
    ) {
        return ResponseEntity.ok(taskService.createTask(newTaskRequest));
    }

    @PutMapping("/{taskId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Изменение задачи",
            description = "Позволяет пользователю изменить созданную задачу"
    )
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskUpdateRequest taskUpdateRequest
    ) throws TaskNotFound {
        return ResponseEntity.ok(taskService.updateTask(taskId, taskUpdateRequest));
    }

    @DeleteMapping("/{taskId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удаление задачи",
            description = "Позволяет пользователю удалить задачу"
    )
    public void deleteTask(
            @PathVariable Long taskId
    ) throws TaskNotFound {
        taskService.deleteTask(taskId);
        ResponseEntity.ok();
    }

    @GetMapping("/by-author")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Просмотр задач конкретного автора",
            description = "Позволяет пользователю получить задачи конкретного автора"
    )
    public ResponseEntity<TasksListResponse> findTasksByAuthor(
            @RequestBody ByEmployeeTaskRequest byEmployeeTaskRequest
    ) throws TaskNotFound {
        return ResponseEntity.ok(taskService.findTasksByAuthor(byEmployeeTaskRequest));
    }

    @GetMapping("/by-executor")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Просмотр задач конкретного исполнителя",
            description = "Позволяет пользователю получить задачи конкретного исполнителя"
    )
    public ResponseEntity<TasksListResponse> findTasksByExecutor(
            @RequestBody ByEmployeeTaskRequest byEmployeeTaskRequest
    ) throws TaskNotFound {
        return ResponseEntity.ok(taskService.findTasksByExecutor(byEmployeeTaskRequest));
    }

    @GetMapping
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Просмотр существующих задач",
            description = "Позволяет пользователю получить список существующих задач"
    )
    public ResponseEntity<TasksListResponse> findAllTasks() {
        return ResponseEntity.ok(taskService.findAllTasks());
    }
}
