package com.example.task.management.system.model.request;

import com.example.task.management.system.model.enums.Priority;
import com.example.task.management.system.model.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на изменение задачи")
public class TaskUpdateRequest {
    @NotBlank
    private String header;
    @NotBlank
    private String description;
    @NotNull
    private Status status;
    @NotNull
    private Priority priority;
    @NotNull
    private List<Long> executors;
}
