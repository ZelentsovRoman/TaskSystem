package com.example.sys.controller;

import com.example.sys.models.*;
import com.example.sys.repostiories.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/auth")
    public Object auth(@RequestBody String json) {
        User user = new Gson().fromJson(json, User.class);
        User findedUser = userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (findedUser == null) {
            return HttpStatus.NOT_FOUND;
        } else return new Gson().toJson(findedUser);
    }

    @GetMapping("/allTasks")
    public String getTasks() {
        ArrayList<Task> list = taskRepository.findAllByOrderByTaskId();
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        String json = gson.toJson(list);
        return json;
    }

    @PostMapping("/TaskForAdmin")
    public String getTasksForAdmin(@RequestBody String json) {
        User user = new Gson().fromJson(json, User.class);
        ArrayList<Task> list = taskRepository.findByCompanyId(user.getEmployee().getCompany().getCompanyId());
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        String Jsonlist = gson.toJson(list);
        return Jsonlist;
    }

    @PostMapping("/TaskForUser")
    public String getTasksForUser(@RequestBody String json) {
        User user = new Gson().fromJson(json, User.class);
        ArrayList<Task> list = taskRepository.findAllByEmployeeIdOrderByTaskId(user.getEmployee());
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        String Jsonlist = gson.toJson(list);
        return Jsonlist;
    }

    @PostMapping("/allEmployee")
    public String getEmployee(@RequestBody String js) {
        User user = new Gson().fromJson(js, User.class);
        ArrayList<Employee> list = employeeRepository.findAllByCompanyOrderByLastName(user.getEmployee().getCompany());
        String json = new Gson().toJson(list);
        return json;
    }

    @GetMapping("/getTask/{id}")
    public String getTask(@PathVariable("id") int id) throws ParseException {
        Task task = taskRepository.findByTaskId(id);
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        String json = gson.toJson(task);
        return json;
    }

    @GetMapping("/getSubtasks/{id}")
    public String getSubtasks(@PathVariable("id") int id) throws ParseException {
        ArrayList<Subtask> subtasks = subtaskRepository.findAllByTaskId(id);
        String json = new Gson().toJson(subtasks);
        return json;
    }

    @PostMapping("/saveNewTask")
    public ResponseEntity saveNewTask(@RequestBody String json) {
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        Task task = gson.fromJson(json, Task.class);
        taskRepository.save(task);
        Task newtask = taskRepository.findFirstByDescriptionAndDateAndDateStartAndDateEndAndEmployeeIdAndStatusIdAndUserIdOrderByTaskId(
                task.getDescription(), task.getDate(), task.getDateStart(), task.getDateEnd(), task.getEmployeeId(), task.getStatusId(), task.getUserId());
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("subtasks");

        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            subtasks.add(new Gson().fromJson(arr.get(i).toString(), Subtask.class));
        }
        for (Subtask subtask : subtasks) {
            subtask.setTaskId(newtask.getTaskId());
        }
        ArrayList<Subtask> fromDB = subtaskRepository.findAllByTaskId(newtask.getTaskId());
        ArrayList<Integer> ids = new ArrayList<>();
        for (Subtask sub : subtasks) {
            sub.setTaskId(newtask.getTaskId());
            ids.add(sub.getSubtaskId());
        }
        for (Subtask sub : fromDB) {
            if (!ids.contains(sub.getSubtaskId())) {
                subtaskRepository.deleteById(sub.getSubtaskId());
            }
        }
        if (subtasks.size() == 0) {
            subtaskRepository.deleteByTaskId(newtask.getTaskId());
        }
        subtaskRepository.saveAll(subtasks);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/saveTask")
    public ResponseEntity saveTask(@RequestBody String json) {
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        Task task = gson.fromJson(json, Task.class);
        taskRepository.save(task);
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("subtasks");

        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            subtasks.add(new Gson().fromJson(arr.get(i).toString(), Subtask.class));
        }
        ArrayList<Subtask> fromDB = subtaskRepository.findAllByTaskId(task.getTaskId());
        ArrayList<Integer> ids = new ArrayList<>();
        for (Subtask sub : subtasks) {
            ids.add(sub.getSubtaskId());
        }
        for (Subtask sub : fromDB) {
            if (!ids.contains(sub.getSubtaskId())) {
                subtaskRepository.deleteById(sub.getSubtaskId());
            }
        }
        if (subtasks.size() == 0) {
            subtaskRepository.deleteByTaskId(task.getTaskId());
        }
        subtaskRepository.saveAll(subtasks);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/deleteTask/{id}")
    public ResponseEntity deleteTask(@PathVariable("id") int id) {
        subtaskRepository.deleteByTaskId(id);
        taskRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/deleteUser")
    public ResponseEntity deleteUser(@RequestBody String json) {
        Employee employee = new Gson().fromJson(json,Employee.class);
        userRepository.deleteByEmployeeId(employee.getEmployeeId());
        employeeRepository.deleteById(employee.getEmployeeId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/getStatuses")
    public String getStatuses() {
        ArrayList<Status> status = statusRepository.findAll();
        String json = new Gson().toJson(status);
        return json;
    }

    @PostMapping("/saveCompany")
    public String saveCompany(@RequestBody String json) {
        Company company = new Gson().fromJson(json, Company.class);
        companyRepository.save(company);
        String js = new Gson().toJson(companyRepository.findByName(company.getName()));
        return js;
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@RequestBody String json) {
        Employee employee = new Gson().fromJson(json, Employee.class);
        employeeRepository.save(employee);
        String js = new Gson().toJson(employeeRepository.findFirstByNameAndLastNameOrderByEmployeeIdDesc(employee.getName(), employee.getLastName()));
        return js;
    }

    @PostMapping("/saveUser")
    public HttpStatus saveUser(@RequestBody String json) {
        User user = new Gson().fromJson(json, User.class);
        userRepository.save(user);
        return HttpStatus.OK;
    }

    @PostMapping("/EditUser")
    public HttpStatus editUser(@RequestBody String json) {
        User user = new Gson().fromJson(json, User.class);
        Employee employee = user.getEmployee();
        userRepository.save(user);
        employeeRepository.save(employee);
        return HttpStatus.OK;
    }

    @PostMapping("/update")
    public String update(@RequestBody String json) {
        User user = new Gson().fromJson(json, User.class);
        return new Gson().toJson(userRepository.findByUserId(user.getUserId()));
    }

    @PostMapping("/findUser")
    public String findUser(@RequestBody String json) {
        User user = new Gson().fromJson(json, User.class);
        return new Gson().toJson(userRepository.findByLogin(user.getLogin()));
    }

    @PostMapping("/getCompany")
    public String getCompany(@RequestBody String json) {
        Company company = new Gson().fromJson(json, Company.class);
        return new Gson().toJson(companyRepository.findByName(company.getName()));
    }

}
