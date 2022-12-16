package com.example.sys.repostiories;

import com.example.sys.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface UserRepository extends CrudRepository<User, Integer> {
    ArrayList<User> findAll();
    void deleteByUserId(int id);
}
