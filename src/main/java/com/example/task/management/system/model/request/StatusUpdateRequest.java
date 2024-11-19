package com.example.task.management.system.model.request;

import com.example.task.management.system.model.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на обновление статуса задачи")
public class StatusUpdateRequest {
    @NotNull
    private Status status;
}
