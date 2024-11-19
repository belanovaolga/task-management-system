package com.example.task.management.system.controller;

import com.example.task.management.system.exception.CommentNotFound;
import com.example.task.management.system.model.request.NewCommentRequest;
import com.example.task.management.system.model.response.CommentResponse;
import com.example.task.management.system.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Tag(name = "Контроллер комментариев", description = "Отвечает за все, что связано с комментариями")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Создание комментария",
            description = "Позволяет пользователю создать комментарий к задаче"
    )
    public ResponseEntity<CommentResponse> createComment(
            @RequestBody NewCommentRequest newCommentRequest
    ) {
        return ResponseEntity.ok(commentService.createComment(newCommentRequest));
    }

    @DeleteMapping("/{commentId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удаление комментария",
            description = "Позволяет пользователю удалить комментарий"
    )
    public void deleteComment(
            @PathVariable Long commentId
    ) throws CommentNotFound {
        commentService.deleteComment(commentId);
        ResponseEntity.ok();
    }
}
