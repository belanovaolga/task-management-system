package com.example.task.management.system.service;


import com.example.task.management.system.model.request.ByEmployeeTaskRequest;
import com.example.task.management.system.model.request.NewTaskRequest;
import com.example.task.management.system.model.request.StatusUpdateRequest;
import com.example.task.management.system.model.request.TaskUpdateRequest;
import com.example.task.management.system.model.response.TaskResponse;
import com.example.task.management.system.model.response.TasksListResponse;

public interface TaskService {
    TaskResponse createTask(NewTaskRequest newTaskRequest);
    TaskResponse updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest);
    TaskResponse updateStatus(Long taskId, StatusUpdateRequest statusUpdateRequest);
    void deleteTask(Long taskId);
    TasksListResponse findTasksByAuthor(ByEmployeeTaskRequest byEmployeeTaskRequest);
    TasksListResponse findTasksByExecutor(ByEmployeeTaskRequest byEmployeeTaskRequest);
    TasksListResponse findAllTasks();
}
