package com.example.task.management.system.repository;

import com.example.task.management.system.model.EmployeeEntity;
import com.example.task.management.system.model.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Page<TaskEntity> findAllByAuthor(EmployeeEntity employeeEntity, Pageable pageable);
    Page<TaskEntity> findAllByExecutors(EmployeeEntity employeeEntity, Pageable pageable);
}
