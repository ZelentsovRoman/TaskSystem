package com.example.sys.models;

import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity(name = "tasks")
@Table(name = "tasks", schema = "sys", catalog = "system")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
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
    @ManyToOne
    @JoinColumn(name = "status",referencedColumnName = "status_id")
    private Status statusId;


    public Status getStatusId() {
        return statusId;
    }

    public void setStatusId(Status statusId) {
        this.statusId = statusId;
    }


    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Timestamp getDateStart() {
        return dateStart;
    }

    public void setDateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
    }

    public Timestamp getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Timestamp dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }
}
