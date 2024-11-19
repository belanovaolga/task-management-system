package com.example.task.management.system.mapper;

import com.example.task.management.system.model.TaskEntity;
import com_example_task_management_system_model.NewTaskRequest;
import com_example_task_management_system_model.TaskResponse;
import com_example_task_management_system_model.TasksListResponse;
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
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(taskEntity.getId());
        taskResponse.setHeader(taskEntity.getHeader());
        taskResponse.setDescription(taskEntity.getDescription());
        taskResponse.setStatus(taskEntity.getStatus());
        taskResponse.setPriority(taskEntity.getPriority());
        taskResponse.author(employeeMapper.toEmployeeResponse(taskEntity.getAuthor()));
        taskResponse.setExecutor(employeeMapper.toEmployeeResponseList(taskEntity.getExecutors()));

        if(taskEntity.getComments()!=null) {
            taskResponse.setComments(taskEntity.getComments().stream().map(commentMapper::toCommentResponse).toList());
        }

        return taskResponse;
    }

    public TasksListResponse toTaskListResponse(List<TaskEntity> tasksEntityList) {
        List<TaskResponse> tasksResponse = tasksEntityList.stream().map(this::toTaskResponse).toList();
        TasksListResponse tasksListResponse = new TasksListResponse();
        tasksListResponse.setAllTasksList(tasksResponse);

        return tasksListResponse;
    }
}
