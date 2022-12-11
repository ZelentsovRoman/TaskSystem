package com.example.sys.models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity(name = "tasks")
public class Task {
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int taskId;
    @Column(name = "description")
    private String description;
    @Column(name = "date")
    private Timestamp date;
    @Column(name = "date_start")
    private Timestamp dateStart;
    @Column(name = "date_end")
    private Timestamp dateEnd;
    @OneToOne
    @JoinColumn(name = "employee_id_fk",referencedColumnName = "employee_id")
    private Employee employeeId;
}
