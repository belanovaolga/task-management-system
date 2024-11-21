package com.example.task.management.system.service;

import com.example.task.management.system.exception.ExecutorNotFoundException;
import com.example.task.management.system.exception.TaskNotFound;
import com.example.task.management.system.mapper.TaskMapper;
import com.example.task.management.system.model.*;
import com.example.task.management.system.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskServiceTest {
    private final TaskRepository mockTaskRepository;
    private final TaskMapper taskMapper;
    private final EmployeeServiceImpl mockEmployeeService;
    private final TaskServiceImpl taskService;
    private final EmployeeEntity employee1;
    private final EmployeeEntity employee2;
    private final EmployeeEntity employee3;
    private final List<EmployeeEntity> executors;
    private final TaskEntity task1;
    private final TaskEntity task2;
    private final Optional<TaskEntity> optionalTask;

    public TaskServiceTest() {
        mockTaskRepository = Mockito.mock(TaskRepository.class);
        taskMapper = Mockito.mock(TaskMapper.class);
        mockEmployeeService = Mockito.mock(EmployeeServiceImpl.class);
        taskService = new TaskServiceImpl(mockTaskRepository, taskMapper, mockEmployeeService);

        employee1 = EmployeeEntity.builder().id(1L).username("james").email("james@mail.ru").password("james").role(RoleEnum.USER).build();
        employee2 = EmployeeEntity.builder().id(2L).username("jon").email("jon@mail.ru").password("jon").role(RoleEnum.USER).build();
        employee3 = EmployeeEntity.builder().id(3L).username("bob").email("bob@mail.ru").password("bob").role(RoleEnum.ADMIN).build();

        executors = new ArrayList<>();
        executors.add(employee1);
        executors.add(employee2);
        task1 = TaskEntity.builder().id(1L).header("header").description("come up with a header").status(StatusEnum.PENDING).priority(PriorityEnum.MEDIUM).author(employee2).executors(executors).build();
        task2 = TaskEntity.builder().id(2L).header("new task").description("create new task").status(StatusEnum.PENDING).priority(PriorityEnum.MEDIUM).author(employee3).executors(executors).build();
        optionalTask = Optional.of(task1);
    }

    @Test
    void shouldCreateTask() {
        TaskResponse expectedTask = taskMapper.toTaskResponse(task2);
        List<Long> newExecutors = new ArrayList<>();
        newExecutors.add(employee1.getId());
        newExecutors.add(employee2.getId());
        NewTaskRequest newTaskRequest = new NewTaskRequest(
                "new task",
                "create new task",
                StatusEnum.PENDING,
                PriorityEnum.MEDIUM,
                newExecutors
        );

        Mockito.when(mockEmployeeService.findById(employee1.getId())).thenReturn(employee1);
        Mockito.when(mockEmployeeService.findById(employee2.getId())).thenReturn(employee2);
        Mockito.when(mockEmployeeService.getCurrentEmployee()).thenReturn(employee3);
        Mockito.when(taskMapper.toTaskEntity(newTaskRequest)).thenReturn(task2);
        Mockito.when(mockTaskRepository.save(task2)).thenReturn(task2);

        TaskResponse actualTask = taskService.createTask(newTaskRequest);

        assertEquals(expectedTask, actualTask);
    }

    @Test
    void shouldUpdateTask() {
        TaskResponse expectedTask = taskMapper.toTaskResponse(task1);
        List<Long> newExecutors = new ArrayList<>();
        newExecutors.add(task1.getId());
        newExecutors.add(task2.getId());
        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest(
                "header",
                "come up with a header",
                StatusEnum.PENDING,
                PriorityEnum.MEDIUM,
                newExecutors
        );

        Mockito.when(mockTaskRepository.findById(task1.getId())).thenReturn(optionalTask);
        Mockito.when(mockEmployeeService.findById(employee1.getId())).thenReturn(employee1);
        Mockito.when(mockEmployeeService.findById(employee2.getId())).thenReturn(employee2);
        Mockito.when(mockEmployeeService.getCurrentEmployee()).thenReturn(employee3);
        Mockito.when(mockTaskRepository.save(task1)).thenReturn(task1);

        TaskResponse actualTask = taskService.updateTask(task1.getId(), taskUpdateRequest);

        assertEquals(expectedTask, actualTask);
    }

    @Test
    void shouldUpdateTask_whenTaskNotFound() {
        Mockito.when(mockTaskRepository.findById(task2.getId())).thenReturn(Optional.empty());
        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();

        assertThrows(TaskNotFound.class, () -> {
            taskService.updateTask(task2.getId(), taskUpdateRequest);
        });
    }

    @Test
    void shouldUpdateStatus() {
        TaskResponse expectedTask = taskMapper.toTaskResponse(task1);
        StatusUpdateRequest statusUpdateRequest = new StatusUpdateRequest(StatusEnum.COMPLETED);

        Mockito.when(mockTaskRepository.findById(task1.getId())).thenReturn(optionalTask);
        Mockito.when(mockEmployeeService.getCurrentEmployee()).thenReturn(employee1);
        Mockito.when(mockTaskRepository.save(task1)).thenReturn(task1);

        TaskResponse actualTask = taskService.updateStatus(task1.getId(), statusUpdateRequest);

        assertEquals(expectedTask, actualTask);
    }

    @Test
    void shouldUpdateStatus_whenExecutorNotFoundException() {
        StatusUpdateRequest statusUpdateRequest = new StatusUpdateRequest(StatusEnum.COMPLETED);

        Mockito.when(mockTaskRepository.findById(task1.getId())).thenReturn(optionalTask);
        Mockito.when(mockEmployeeService.getCurrentEmployee()).thenReturn(employee3);

        assertThrows(ExecutorNotFoundException.class, () -> {
            taskService.updateStatus(task1.getId(), statusUpdateRequest);
        });
    }

    @Test
    void shouldDeleteTask() {
        Mockito.when(mockTaskRepository.findById(task1.getId())).thenReturn(optionalTask);
        Mockito.doNothing().when(mockTaskRepository).delete(task1);
        taskService.deleteTask(task1.getId());

        Mockito.when(mockTaskRepository.findById(task1.getId())).thenReturn(Optional.empty());

        assertThrows(TaskNotFound.class, () -> {
            taskService.findById(task1.getId());
        });
    }

    @Test
    void shouldFindTasksByAuthor() {
        List<TaskResponse> taskList = new ArrayList<>();
        taskList.add(taskMapper.toTaskResponse(task1));
        TasksListResponse expectedTasksListResponse = new TasksListResponse();
        expectedTasksListResponse.setAllTasksList(taskList);

        Mockito.when(mockEmployeeService.findById(employee2.getId())).thenReturn(employee2);
        List<TaskEntity> taskEntityList = new ArrayList<>();
        taskEntityList.add(task1);
        Pageable pageable = PageRequest.of(0, 5);
        Page<TaskEntity> taskEntityPage = new PageImpl<>(taskEntityList);
        Mockito.when(mockTaskRepository.findAllByAuthor(employee2, pageable)).thenReturn(taskEntityPage);
        Mockito.when(taskMapper.toTaskListResponse(taskEntityList)).thenReturn(expectedTasksListResponse);

        TasksListResponse actualTasksListResponse = taskService.findTasksByAuthor(employee2.getId(), 0);

        assertEquals(expectedTasksListResponse, actualTasksListResponse);
    }

    @Test
    void shouldFindTasksByExecutor() {
        List<TaskResponse> taskList = new ArrayList<>();
        taskList.add(taskMapper.toTaskResponse(task1));
        taskList.add(taskMapper.toTaskResponse(task2));
        TasksListResponse expectedTasksListResponse = new TasksListResponse();
        expectedTasksListResponse.setAllTasksList(taskList);

        Mockito.when(mockEmployeeService.findById(employee2.getId())).thenReturn(employee2);
        List<TaskEntity> taskEntityList = new ArrayList<>();
        taskEntityList.add(task1);
        taskEntityList.add(task2);
        Pageable pageable = PageRequest.of(0, 5);
        Page<TaskEntity> taskEntityPage = new PageImpl<>(taskEntityList);
        Mockito.when(mockTaskRepository.findAllByExecutors(employee2, pageable)).thenReturn(taskEntityPage);
        Mockito.when(taskMapper.toTaskListResponse(taskEntityList)).thenReturn(expectedTasksListResponse);

        TasksListResponse actualTasksListResponse = taskService.findTasksByExecutor(employee2.getId(), 0);

        assertEquals(expectedTasksListResponse, actualTasksListResponse);
    }

    @Test
    void shouldFindAllTasks() {
        List<TaskResponse> taskList = new ArrayList<>();
        taskList.add(taskMapper.toTaskResponse(task1));
        taskList.add(taskMapper.toTaskResponse(task2));
        TasksListResponse expectedTasksListResponse = new TasksListResponse();
        expectedTasksListResponse.setAllTasksList(taskList);

        List<TaskEntity> taskEntityList = new ArrayList<>();
        taskEntityList.add(task1);
        taskEntityList.add(task2);
        Pageable pageable = PageRequest.of(0, 5);
        Page<TaskEntity> taskEntityPage = new PageImpl<>(taskEntityList);
        Mockito.when(mockTaskRepository.findAll(pageable)).thenReturn(taskEntityPage);
        Mockito.when(taskMapper.toTaskListResponse(taskEntityList)).thenReturn(expectedTasksListResponse);

        TasksListResponse actualTasksListResponse = taskService.findAllTasks(0);

        assertEquals(expectedTasksListResponse, actualTasksListResponse);
    }
}
