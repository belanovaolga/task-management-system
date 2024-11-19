package com.example.task.management.system.repository;

import com.example.task.management.system.model.EmployeeEntity;
import com.example.task.management.system.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findAllByAuthor(EmployeeEntity employeeEntity);
    List<TaskEntity> findAllByExecutor(EmployeeEntity employeeEntity);
}
