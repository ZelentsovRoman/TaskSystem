package com.example.sys.repostiories;

import com.example.sys.models.Employee;
import com.example.sys.models.Status;
import com.example.sys.models.Task;
import com.example.sys.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    ArrayList<Task> findAllByOrderByTaskId();

    Task findByTaskId(int taskId);

    ArrayList<Task> findAllByUserIdOrderByTaskId(User user);

    @Modifying
    @Transactional
    @Query("SELECT a from tasks a inner join employee e on e.employeeId = a.employeeId.employeeId inner join companies c on c.companyId = e.company.companyId where e.company.companyId=:company_id order by a.priority desc, a.taskId")
    ArrayList<Task> findAllByCompanyIdOrderByPriorityDescTaskIdAsc(@Param("company_id") int company_id);

    ArrayList<Task> findAllByEmployeeIdOrderByPriorityDescTaskIdAsc(Employee employee);

    Task findFirstByDescriptionAndDateAndDateStartAndDateEndAndEmployeeIdAndStatusIdAndPriorityAndUserIdOrderByTaskId(String desc, Timestamp date, Timestamp dateStart,
                                                                                                           Timestamp dateEnd, Employee emp, Status stat, String priority, User user);
}
