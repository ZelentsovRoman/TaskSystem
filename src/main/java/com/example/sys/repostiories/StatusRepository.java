package com.example.sys.repostiories;

import com.example.sys.models.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface StatusRepository extends CrudRepository<Status, Integer> {
    ArrayList<Status> findAll();
}
