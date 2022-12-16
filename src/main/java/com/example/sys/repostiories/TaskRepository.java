package com.example.sys.repostiories;

import com.example.sys.models.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface TaskRepository extends CrudRepository<Task,Integer> {
    ArrayList<Task> findAllByOrderByTaskId();
    Task findByTaskId(int taskId);

    @Query(value = "SELECT nextval('sys.tasks_task_id_seq')", nativeQuery = true)
    public int getNextValMySequence();
}
