package com.example.sys.models;

import jakarta.persistence.*;

@Entity(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    private int employeeId;
    @OneToOne
    @JoinColumn(name = "user_id_fk", referencedColumnName = "user_id")
    private User userId;
    @Column(name = "name")
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "company")
    private String company;
    @Column(name = "privileges")
    private String privileges;
}
