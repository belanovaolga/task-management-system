package com.example.task.management.system.controller;

import com.example.task.management.system.api.TasksApi;
import com.example.task.management.system.model.*;
import com.example.task.management.system.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskController implements TasksApi {
    private final TaskService taskService;

    @Override
    public ResponseEntity<TaskResponse> createTask(NewTaskRequest newTaskRequest) {
        return ResponseEntity.ok(taskService.createTask(newTaskRequest));
    }

    @Override
    public ResponseEntity<TaskResponse> updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest) {
        return ResponseEntity.ok(taskService.updateTask(taskId, taskUpdateRequest));
    }

    @Override
    public ResponseEntity<TaskResponse> updateStatus(Long taskId, StatusUpdateRequest statusUpdateRequest) {
        return ResponseEntity.ok(taskService.updateStatus(taskId, statusUpdateRequest));
    }

    @Override
    public ResponseEntity<Void> deleteTask(Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<TasksListResponse> taskByAuthor(Long authorId, Integer page) {
        return ResponseEntity.ok(taskService.findTasksByAuthor(authorId, page));
    }

    @Override
    public ResponseEntity<TasksListResponse> taskByExecutor(Long executorId, Integer page) {
        return ResponseEntity.ok(taskService.findTasksByExecutor(executorId, page));
    }

    @Override
    public ResponseEntity<TasksListResponse> allTasks(Integer page) {
        return ResponseEntity.ok(taskService.findAllTasks(page));
    }
}
