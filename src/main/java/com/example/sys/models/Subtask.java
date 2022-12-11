package com.example.sys.models;

import jakarta.persistence.*;

@Entity(name = "subtasks")
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subtask_id")
    private int subtaskId;
    @Column(name = "decription")
    private String description;
    @Column(name = "value")
    private boolean value;
    @ManyToOne
    @JoinColumn(name = "task_id_fk", referencedColumnName = "task_id")
    private Task taskId;
}
