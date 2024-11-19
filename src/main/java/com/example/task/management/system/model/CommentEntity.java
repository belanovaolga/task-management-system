package com.example.task.management.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentSeqGen")
    @SequenceGenerator(name = "commentSeqGen", sequenceName = "comment_sequence_name", allocationSize = 1)
    private Long id;
    private String text;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TaskEntity task;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private EmployeeEntity author;
}
