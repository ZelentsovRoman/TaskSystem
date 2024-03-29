package com.example.sys.repostiories;

import com.example.sys.models.Company;
import com.example.sys.models.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    ArrayList<Employee> findAllByOrderByLastName();

    ArrayList<Employee> findAllByCompanyOrderByLastName(Company company);

    Employee findFirstByNameAndLastNameOrderByEmployeeIdDesc(String name, String lastName);
}
