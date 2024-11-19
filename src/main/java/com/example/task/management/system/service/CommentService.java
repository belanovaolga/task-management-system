package com.example.task.management.system.service;

import com.example.task.management.system.model.request.NewCommentRequest;
import com.example.task.management.system.model.response.CommentResponse;

public interface CommentService {
    CommentResponse createComment(NewCommentRequest newCommentRequest);
    void deleteComment(Long commentId);
}
