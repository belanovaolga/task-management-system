package com.example.task.management.system.mapper;

import com.example.task.management.system.model.CommentEntity;
import com.example.task.management.system.model.CommentResponse;
import com.example.task.management.system.model.NewCommentRequest;
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
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(commentEntity.getId());
        commentResponse.setText(commentEntity.getText());
        commentResponse.setTaskId(commentEntity.getTask().getId());
        commentResponse.setAuthor(employeeMapper.toEmployeeResponse(commentEntity.getAuthor()));

        return commentResponse;
    }
}
