package com.example.sys.repostiories;

import com.example.sys.models.Subtask;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface SubtaskRepository extends CrudRepository<Subtask, Integer> {
    ArrayList<Subtask> findAllByTaskId(int id);

    List<Subtask> findAll();

    @Modifying
    @Transactional
    @Query("delete from subtasks b where b.taskId=:task_id_fk")
    void deleteByTaskId(@Param("task_id_fk") int task_id_fk);

    @Override
    void deleteById(Integer integer);
}
