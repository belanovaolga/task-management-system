package com.example.task.management.system.service;

import com_example_task_management_system_model.NewTaskRequest;
import com_example_task_management_system_model.StatusUpdateRequest;
import com_example_task_management_system_model.TaskResponse;
import com_example_task_management_system_model.TaskUpdateRequest;
import com_example_task_management_system_model.TasksListResponse;

public interface TaskService {
    TaskResponse createTask(NewTaskRequest newTaskRequest);
    TaskResponse updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest);
    TaskResponse updateStatus(Long taskId, StatusUpdateRequest statusUpdateRequest);
    void deleteTask(Long taskId);
    TasksListResponse findTasksByAuthor(Long authorId);
    TasksListResponse findTasksByExecutor(Long executorId);
    TasksListResponse findAllTasks();
}
