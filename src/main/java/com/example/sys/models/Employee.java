package com.example.sys.models;

import jakarta.persistence.*;

@Entity(name = "employee")
@Table(name = "employee", schema = "sys", catalog = "system")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employeeId;
    @Column(name = "name")
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @OneToOne
    @JoinColumn(name = "company_id_fk", referencedColumnName = "company_id")
    private Company company;
    @Column(name = "privileges")
    private String privileges;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }
}
