package com.example.task.management.system.service;

import com.example.task.management.system.model.*;

public interface TaskService {
    TaskResponse createTask(NewTaskRequest newTaskRequest);
    TaskResponse updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest);
    TaskResponse updateStatus(Long taskId, StatusUpdateRequest statusUpdateRequest);
    void deleteTask(Long taskId);
    TasksListResponse findTasksByAuthor(Long authorId);
    TasksListResponse findTasksByExecutor(Long executorId);
    TasksListResponse findAllTasks();
}
