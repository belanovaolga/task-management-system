package com.example.task.management.system.service;

import com.example.task.management.system.exception.ExecutorNotFoundException;
import com.example.task.management.system.exception.TaskNotFound;
import com.example.task.management.system.mapper.TaskMapper;
import com.example.task.management.system.model.CommentEntity;
import com.example.task.management.system.model.EmployeeEntity;
import com.example.task.management.system.model.TaskEntity;
import com.example.task.management.system.repository.TaskRepository;
import com_example_task_management_system_model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final EmployeeServiceImpl employeeService;

    @Transactional
    @Override
    public TaskResponse createTask(NewTaskRequest newTaskRequest) {
        List<EmployeeEntity> executorsEntitiesList = newTaskRequest.getExecutors().stream()
                .map(employeeService::findById).toList();
        EmployeeEntity currentEmployee = employeeService.getCurrentEmployee();

        TaskEntity taskEntity = taskMapper.toTaskEntity(newTaskRequest);
        taskEntity.setExecutors(executorsEntitiesList);
        taskEntity.setAuthor(currentEmployee);

        TaskEntity newTask = taskRepository.save(taskEntity);

        return taskMapper.toTaskResponse(newTask);
    }

    @Transactional
    @Override
    public TaskResponse updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest) {
        TaskEntity taskEntity = findById(taskId);
        List<EmployeeEntity> executorsEntitiesList = taskUpdateRequest.getExecutors().stream()
                .map(employeeService::findById).toList();

        taskEntity.setHeader(taskUpdateRequest.getHeader());
        taskEntity.setDescription(taskUpdateRequest.getDescription());
        taskEntity.setStatus(taskUpdateRequest.getStatus());
        taskEntity.setPriority(taskUpdateRequest.getPriority());
        taskEntity.setExecutors(executorsEntitiesList);

        TaskEntity updatedTask = taskRepository.save(taskEntity);

        return taskMapper.toTaskResponse(updatedTask);
    }

    @Transactional
    @Override
    public TaskResponse updateStatus(Long taskId, StatusUpdateRequest statusUpdateRequest) {
        TaskEntity taskEntity = findById(taskId);
        if(!taskEntity.getExecutors().contains(employeeService.getCurrentEmployee())) {
            throw new ExecutorNotFoundException();
        }
        taskEntity.setStatus(statusUpdateRequest.getStatus());

        TaskEntity updatedTask = taskRepository.save(taskEntity);

        return taskMapper.toTaskResponse(updatedTask);
    }

    @Override
    public void deleteTask(Long taskId) {
        TaskEntity taskEntity = findById(taskId);

        taskRepository.delete(taskEntity);
    }

    @Override
    public TasksListResponse findTasksByAuthor(Long authorId) {
        EmployeeEntity author = employeeService.findById(authorId);

        List<TaskEntity> tasksByAuthor = taskRepository.findAllByAuthor(author);

        return taskMapper.toTaskListResponse(tasksByAuthor);
    }

    @Override
    public TasksListResponse findTasksByExecutor(Long executorId) {
        EmployeeEntity executor = employeeService.findById(executorId);

        List<TaskEntity> tasksByExecutor = taskRepository.findAllByExecutor(executor);

        return taskMapper.toTaskListResponse(tasksByExecutor);
    }

    @Override
    public TasksListResponse findAllTasks() {
        List<TaskEntity> tasksEntityList = taskRepository.findAll();

        return taskMapper.toTaskListResponse(tasksEntityList);
    }

    protected void addComment(CommentEntity commentEntity) {
        TaskEntity taskEntity = commentEntity.getTask();
        List<CommentEntity> comments = taskEntity.getComments();
        comments.add(commentEntity);

        taskEntity.setComments(comments);
        taskRepository.save(taskEntity);
    }

    protected TaskEntity findById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(TaskNotFound::new);
    }
}
