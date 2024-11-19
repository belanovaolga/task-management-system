package com.example.task.management.system.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "Ответ с информацией о комментарии")
public class CommentResponse {
    private Long id;
    private String text;
    private Long taskId;
    private EmployeeResponse author;
}
