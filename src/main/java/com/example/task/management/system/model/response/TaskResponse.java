package com.example.task.management.system.model.response;

import com.example.task.management.system.model.enums.Priority;
import com.example.task.management.system.model.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с задачей")
public class TaskResponse {
    private Long id;
    private String header;
    private String description;
    private Status status;
    private Priority priority;
    private List<CommentResponse> comments;
    private EmployeeResponse author;
    private List<EmployeeResponse> executors;
}
