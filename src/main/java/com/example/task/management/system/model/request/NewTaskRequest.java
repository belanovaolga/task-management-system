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

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на создание задачи")
public class NewTaskRequest {
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
