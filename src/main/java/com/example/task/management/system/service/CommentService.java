package com.example.task.management.system.service;

import com.example.task.management.system.model.CommentResponse;
import com.example.task.management.system.model.NewCommentRequest;

public interface CommentService {
    CommentResponse createComment(NewCommentRequest newCommentRequest);
    void deleteComment(Long commentId);
}
