package com.example.sys.controller;

import com.example.sys.models.*;
import com.example.sys.repostiories.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private SubtaskRepository subtaskRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private StatusRepository statusRepository;

    @GetMapping("/allTasks")
    public String getTasks(){
        ArrayList<Task> list = taskRepository.findAllByOrderByTaskId();
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        String json = gson.toJson(list);
        return json;
    }
    @GetMapping("/allEmployee")
    public String getEmployee(){
        ArrayList<Employee> list = employeeRepository.findAllByOrderByLastName();
        String json = new Gson().toJson(list);
        return json;
    }
    @GetMapping("/getTask/{id}")
    public String getTask(@PathVariable("id") int id) throws ParseException {
        Task task= taskRepository.findByTaskId(id);
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        String json = gson.toJson(task);
        return json;
    }
    @GetMapping("/getSubtasks/{id}")
    public String getSubtasks(@PathVariable("id") int id) throws ParseException {
        ArrayList<Subtask> subtasks =subtaskRepository.findAllByTaskId(id);
        String json = new Gson().toJson(subtasks);
        return json;
    }
    @PostMapping("/saveTask")
    public String saveTask(@RequestBody String json){
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        Task task = gson.fromJson(json, Task.class);
        String js = new Gson().toJson(taskRepository.findFirstByDescriptionAndDateAndDateStartAndDateEndAndEmployeeIdAndStatusIdOrderByTaskId(
                task.getDescription(),task.getDate(),task.getDateStart(),task.getDateEnd(),task.getEmployeeId(),task.getStatusId()));
        return js;
    }
    @PostMapping("/saveSubtask/{id}")
    public ResponseEntity saveSubtasks( @PathVariable("id") int id,@RequestBody String json){
        Type listType = new TypeToken<ArrayList<Subtask>>() {}.getType();
        ArrayList<Subtask> subtasks = new Gson().fromJson(json,listType);
        ArrayList<Subtask> fromDB = subtaskRepository.findAllByTaskId(id);
        ArrayList<Integer> ids = new ArrayList<>();
        for(Subtask sub: subtasks){
            ids.add(sub.getSubtaskId());
        }
        for(Subtask sub: fromDB){
            if(!ids.contains(sub.getSubtaskId())){
                subtaskRepository.deleteById(sub.getSubtaskId());
            }
        }
        if(subtasks.size()==0) {
            subtaskRepository.deleteByTaskId(id);
        }
        subtaskRepository.saveAll(subtasks);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/saveSubtask")
    public ResponseEntity saveSubtasks(@RequestBody String json){
        Type listType = new TypeToken<ArrayList<Subtask>>() {}.getType();
        ArrayList<Subtask> subtasks = new Gson().fromJson(json,listType);
        subtaskRepository.saveAll(subtasks);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/deleteTask/{id}")
    public ResponseEntity deleteTask(@PathVariable("id") int id){
        subtaskRepository.deleteByTaskId(id);
        taskRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/getStatuses")
    public String getStatuses(){
        ArrayList<Status> status= statusRepository.findAll();
        String json = new Gson().toJson(status);
        return json;
    }
    @PostMapping("/saveCompany")
    public String saveCompany(@RequestBody String json){
        Company company = new Gson().fromJson(json, Company.class);
        companyRepository.save(company);
        String js = new Gson().toJson(companyRepository.findByName(company.getName()));
        return js;
    }
    @PostMapping("/saveEmployee")
    public String saveEmployee(@RequestBody String json){
        Employee employee = new Gson().fromJson(json, Employee.class);
        employeeRepository.save(employee);
        String js = new Gson().toJson(employeeRepository.findFirstByNameAndLastNameOrderByEmployeeIdDesc(employee.getName(), employee.getLastName()));
        return js;
    }
    @PostMapping("/saveUser")
    public HttpStatus saveUser(@RequestBody String json){
        User user = new Gson().fromJson(json, User.class);
        userRepository.save(user);
        return HttpStatus.OK;
    }
}
