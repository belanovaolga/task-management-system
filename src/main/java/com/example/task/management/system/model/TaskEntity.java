package com.example.task.management.system.model;

import com_example_task_management_system_model.PriorityEnum;
import com_example_task_management_system_model.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taskSeqGen")
    @SequenceGenerator(name = "taskSeqGen", sequenceName = "task_sequence_name", allocationSize = 1)
    private Long id;
    private String header;
    private String description;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Enumerated(EnumType.STRING)
    private PriorityEnum priority;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private EmployeeEntity author;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmployeeEntity> executors;
}
