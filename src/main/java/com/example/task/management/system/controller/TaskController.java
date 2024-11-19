package com.example.task.management.system.controller;

import com.example.task.management.system.service.TaskService;
import com_example_task_management_system_api.TasksApi;
import com_example_task_management_system_model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskController implements TasksApi {
    private final TaskService taskService;

    @Override
    public ResponseEntity<TaskResponse> createTask(NewTaskRequest newTaskRequest) {
        taskService.createTask(newTaskRequest);
        return TasksApi.super.createTask(newTaskRequest);
    }

    @Override
    public ResponseEntity<TaskResponse> updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest) {
        taskService.updateTask(taskId, taskUpdateRequest);
        return TasksApi.super.updateTask(taskId, taskUpdateRequest);
    }

    @Override
    public ResponseEntity<TaskResponse> updateStatus(Long taskId, StatusUpdateRequest statusUpdateRequest) {
        taskService.updateStatus(taskId, statusUpdateRequest);
        return TasksApi.super.updateStatus(taskId, statusUpdateRequest);
    }

    @Override
    public ResponseEntity<Void> deleteTask(Long taskId) {
        taskService.deleteTask(taskId);
        return TasksApi.super.deleteTask(taskId);
    }

    @Override
    public ResponseEntity<TasksListResponse> taskByAuthor(Long authorId) {
        taskService.findTasksByAuthor(authorId);
        return TasksApi.super.taskByAuthor(authorId);
    }

    @Override
    public ResponseEntity<TasksListResponse> taskByExecutor(Long executorId) {
        taskService.findTasksByExecutor(executorId);
        return TasksApi.super.taskByExecutor(executorId);
    }

    @Override
    public ResponseEntity<TasksListResponse> allTasks() {
        taskService.findAllTasks();
        return TasksApi.super.allTasks();
    }
}
