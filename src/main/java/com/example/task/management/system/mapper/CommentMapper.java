package com.example.task.management.system.mapper;

import com.example.task.management.system.model.CommentEntity;
import com.example.task.management.system.model.request.NewCommentRequest;
import com.example.task.management.system.model.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final EmployeeMapper employeeMapper;

    public CommentEntity toCommentEntity(NewCommentRequest newCommentRequest) {
        return CommentEntity.builder()
                .text(newCommentRequest.getText())
                .build();
    }

    public CommentResponse toCommentResponse(CommentEntity commentEntity) {
        return CommentResponse.builder()
                .id(commentEntity.getId())
                .text(commentEntity.getText())
                .taskId(commentEntity.getTask().getId())
                .author(employeeMapper.toEmployeeResponse(commentEntity.getAuthor()))
                .build();
    }
}
