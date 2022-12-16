package com.example.sys.repostiories;

import com.example.sys.models.Employee;
import com.example.sys.models.Status;
import com.example.sys.models.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

public interface TaskRepository extends CrudRepository<Task,Integer> {
    ArrayList<Task> findAllByOrderByTaskId();
    Task findByTaskId(int taskId);
    Task findFirstByDescriptionAndDateAndDateStartAndDateEndAndEmployeeIdAndStatusIdOrderByTaskId(String desc, Timestamp date, Timestamp dateStart,
                                                                                                  Timestamp dateEnd, Employee emp, Status stat);
}
