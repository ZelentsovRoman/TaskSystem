package com.example.sys.models;

import jakarta.persistence.*;

@Entity(name = "subtasks")
@Table(name = "subtasks", schema = "sys", catalog = "system")
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subtask_id")
    private int subtaskId;
    @Column(name = "description")
    private String description;
    @Column(name = "value")
    private boolean value;
    @Column(name = "task_id_fk")
    private int taskId;

    public int getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(int subtaskId) {
        this.subtaskId = subtaskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
