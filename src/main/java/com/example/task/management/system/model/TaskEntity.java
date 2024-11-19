package com.example.task.management.system.model;

import com.example.task.management.system.model.enums.Priority;
import com.example.task.management.system.model.enums.Status;
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
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private EmployeeEntity author;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmployeeEntity> executors;
}
