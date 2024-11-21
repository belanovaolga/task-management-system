package com.example.task.management.system.service;

import com.example.task.management.system.exception.CommentNotFound;
import com.example.task.management.system.exception.ExecutorNotFoundException;
import com.example.task.management.system.mapper.CommentMapper;
import com.example.task.management.system.model.*;
import com.example.task.management.system.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommentServiceTest {
    private final CommentRepository mockCommentRepository;
    private final CommentMapper mockCommentMapper;
    private final EmployeeServiceImpl mockEmployeeService;
    private final TaskServiceImpl mockTaskService;
    private final CommentServiceImpl commentService;
    private final EmployeeEntity employee;
    private final TaskEntity task;
    private final CommentEntity commentEntity;

    public CommentServiceTest() {
        mockCommentRepository = Mockito.mock(CommentRepository.class);
        mockCommentMapper = Mockito.mock(CommentMapper.class);
        mockEmployeeService = Mockito.mock(EmployeeServiceImpl.class);
        mockTaskService = Mockito.mock(TaskServiceImpl.class);
        commentService = new CommentServiceImpl(mockCommentRepository, mockCommentMapper, mockEmployeeService, mockTaskService);

        employee = EmployeeEntity.builder().id(1L).username("james").email("james@mail.ru").password("james").role(RoleEnum.USER).build();

        List<EmployeeEntity> executors = new ArrayList<>();
        executors.add(employee);
        task = TaskEntity.builder().id(1L).header("header").description("come up with a header").status(StatusEnum.PENDING).priority(PriorityEnum.MEDIUM).author(employee).executors(executors).build();
        commentEntity = CommentEntity.builder().id(1L).text("new comment").task(task).author(employee).build();
    }

    @Test
    void shouldCreateComment() {
        NewCommentRequest newCommentRequest = new NewCommentRequest("new comment", task.getId());
        CommentResponse expectedComment = mockCommentMapper.toCommentResponse(commentEntity);

        Mockito.when(mockEmployeeService.getCurrentEmployee()).thenReturn(employee);
        Mockito.when(mockTaskService.findById(task.getId())).thenReturn(task);
        Mockito.when(mockCommentMapper.toCommentEntity(newCommentRequest)).thenReturn(commentEntity);
        Mockito.when(mockCommentRepository.save(commentEntity)).thenReturn(commentEntity);
        Mockito.doNothing().when(mockTaskService).addComment(commentEntity);

        CommentResponse actualComment = commentService.createComment(newCommentRequest);

        assertEquals(expectedComment, actualComment);
    }

    @Test
    void shouldCreateComment_whenExecutorNotFoundException() {
        NewCommentRequest newCommentRequest = new NewCommentRequest("new comment", task.getId());
        EmployeeEntity employee2 = EmployeeEntity.builder().id(1L).username("jon").email("jon@mail.ru").password("jon").role(RoleEnum.USER).build();

        Mockito.when(mockEmployeeService.getCurrentEmployee()).thenReturn(employee2);
        Mockito.when(mockTaskService.findById(task.getId())).thenReturn(task);

        assertThrows(ExecutorNotFoundException.class, () -> {
            commentService.createComment(newCommentRequest);
        });
    }

    @Test
    void shouldDeleteTask() {
        Optional<CommentEntity> optionalComment = Optional.of(commentEntity);
        Mockito.when(mockCommentRepository.findById(commentEntity.getId())).thenReturn(optionalComment);
        Mockito.doNothing().when(mockCommentRepository).delete(commentEntity);
        commentService.deleteComment(commentEntity.getId());

        Mockito.when(mockCommentRepository.findById(commentEntity.getId())).thenReturn(Optional.empty());

        assertThrows(CommentNotFound.class, () -> {
            commentService.findById(commentEntity.getId());
        });
    }
}
