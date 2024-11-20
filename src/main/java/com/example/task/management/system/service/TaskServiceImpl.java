package com.example.task.management.system.service;

import com.example.task.management.system.exception.ExecutorNotFoundException;
import com.example.task.management.system.exception.TaskNotFound;
import com.example.task.management.system.mapper.TaskMapper;
import com.example.task.management.system.model.*;
import com.example.task.management.system.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public TasksListResponse findTasksByAuthor(Long authorId, Integer page) {
        EmployeeEntity author = employeeService.findById(authorId);

        Pageable pageable = PageRequest.of(page, 5);
        Page<TaskEntity> allByAuthor = taskRepository.findAllByAuthor(author, pageable);

        List<TaskEntity> tasksByAuthor = allByAuthor.stream().toList();

        return taskMapper.toTaskListResponse(tasksByAuthor);
    }

    @Override
    public TasksListResponse findTasksByExecutor(Long executorId, Integer page) {
        EmployeeEntity executor = employeeService.findById(executorId);

        Pageable pageable = PageRequest.of(page, 5);
        Page<TaskEntity> allByExecutors = taskRepository.findAllByExecutors(executor, pageable);

        List<TaskEntity> tasksByExecutor = allByExecutors.stream().toList();

        return taskMapper.toTaskListResponse(tasksByExecutor);
    }

    @Override
    public TasksListResponse findAllTasks(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<TaskEntity> allTasks = taskRepository.findAll(pageable);

        List<TaskEntity> tasksEntityList = allTasks.stream().toList();

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
