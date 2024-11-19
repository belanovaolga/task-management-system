package com.example.task.management.system.service;

import com.example.task.management.system.exception.CommentNotFound;
import com.example.task.management.system.exception.ExecutorNotFoundException;
import com.example.task.management.system.mapper.CommentMapper;
import com.example.task.management.system.model.CommentEntity;
import com.example.task.management.system.model.EmployeeEntity;
import com.example.task.management.system.model.TaskEntity;
import com.example.task.management.system.repository.CommentRepository;
import com_example_task_management_system_model.CommentResponse;
import com_example_task_management_system_model.NewCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final EmployeeServiceImpl employeeService;
    private final TaskServiceImpl taskService;

    @Transactional
    @Override
    public CommentResponse createComment(NewCommentRequest newCommentRequest) {
        EmployeeEntity currentEmployee = employeeService.getCurrentEmployee();
        TaskEntity taskEntity = taskService.findById(newCommentRequest.getTaskId());
        if(!taskEntity.getExecutors().contains(currentEmployee)) {
            throw new ExecutorNotFoundException();
        }

        CommentEntity commentEntity = commentMapper.toCommentEntity(newCommentRequest);
        commentEntity.setAuthor(currentEmployee);
        commentEntity.setTask(taskEntity);

        CommentEntity newComment = commentRepository.save(commentEntity);

        taskService.addComment(newComment);

        return commentMapper.toCommentResponse(newComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        CommentEntity commentEntity = findById(commentId);

        commentRepository.delete(commentEntity);
    }

    private CommentEntity findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(CommentNotFound::new);
    }
}
