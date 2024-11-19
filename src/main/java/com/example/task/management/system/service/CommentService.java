package com.example.task.management.system.service;

import com_example_task_management_system_model.CommentResponse;
import com_example_task_management_system_model.NewCommentRequest;

public interface CommentService {
    CommentResponse createComment(NewCommentRequest newCommentRequest);
    void deleteComment(Long commentId);
}
