package com.example.sys.repostiories;

import com.example.sys.models.Employee;
import com.example.sys.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface UserRepository extends CrudRepository<User, Integer> {
    ArrayList<User> findAll();

    User findByUserId(int id);

    User findByLogin(String log);

    User findByLoginAndPassword(String login, String password);
    @Modifying
    @Transactional
    @Query("delete from users b where b.employee.employeeId=:employee_id_fk")
    void deleteByEmployeeId(@Param("employee_id_fk") int employee_id_fk);
}
