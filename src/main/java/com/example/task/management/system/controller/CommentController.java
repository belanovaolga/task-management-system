package com.example.task.management.system.controller;

import com.example.task.management.system.service.CommentService;
import com_example_task_management_system_api.CommentsApi;
import com_example_task_management_system_model.CommentResponse;
import com_example_task_management_system_model.NewCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController implements CommentsApi {
    private final CommentService commentService;

    @Override
    public ResponseEntity<CommentResponse> createComment(NewCommentRequest newCommentRequest) {
        commentService.createComment(newCommentRequest);
        return CommentsApi.super.createComment(newCommentRequest);
    }

    @Override
    public ResponseEntity<Void> deleteComment(Long commentId) {
        commentService.deleteComment(commentId);
        return CommentsApi.super.deleteComment(commentId);
    }
}
