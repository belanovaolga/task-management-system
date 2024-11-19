package com.example.task.management.system.mapper;

import com.example.task.management.system.model.TaskEntity;
import com.example.task.management.system.model.request.NewTaskRequest;
import com.example.task.management.system.model.response.TaskResponse;
import com.example.task.management.system.model.response.TasksListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskMapper {
    private final EmployeeMapper employeeMapper;
    private final CommentMapper commentMapper;

    public TaskEntity toTaskEntity(NewTaskRequest newTaskRequest) {
        return TaskEntity.builder()
                .header(newTaskRequest.getHeader())
                .description(newTaskRequest.getDescription())
                .status(newTaskRequest.getStatus())
                .priority(newTaskRequest.getPriority())
                .build();
    }

    public TaskResponse toTaskResponse(TaskEntity taskEntity) {
        TaskResponse taskResponse = TaskResponse.builder()
                .id(taskEntity.getId())
                .header(taskEntity.getHeader())
                .description(taskEntity.getDescription())
                .status(taskEntity.getStatus())
                .priority(taskEntity.getPriority())
                .author(employeeMapper.toEmployeeResponse(taskEntity.getAuthor()))
                .executors(employeeMapper.toEmployeeResponseList(taskEntity.getExecutors()))
                .build();

        if(taskEntity.getComments()!=null) {
            taskResponse.setComments(taskEntity.getComments().stream().map(commentMapper::toCommentResponse).toList());
        }

        return taskResponse;
    }

    public TasksListResponse toTaskListResponse(List<TaskEntity> tasksEntityList) {
        List<TaskResponse> tasksResponse = tasksEntityList.stream().map(this::toTaskResponse).toList();

        return TasksListResponse.builder().allTasksList(tasksResponse).build();
    }
}
