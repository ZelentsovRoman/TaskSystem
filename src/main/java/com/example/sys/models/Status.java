package com.example.sys.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "statuses")
@Table(name = "statuses", schema = "sys", catalog = "system")
public class Status {
    @Id
    @Column(name = "status_id")
    private int statusId;
    @Column(name = "status")
    private String status;
}
