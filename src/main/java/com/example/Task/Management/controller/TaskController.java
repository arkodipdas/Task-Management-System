package com.example.Task.Management.controller;

import com.example.Task.Management.entity.TaskEntity;
import com.example.Task.Management.entity.UserEntity;
import com.example.Task.Management.repository.TaskRepository;
import com.example.Task.Management.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.DeleteExchange;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/create-task")
    public ResponseEntity<?> createTask(@RequestBody TaskEntity task)    // to create a new task
    {
        if(taskService.canCreateTask(task))
        {
            task.setUpdatedBy(task.getCreatedBy());
            taskRepository.save(task);
            return new ResponseEntity<>("Task Added",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Bad Request or Assigning More than 10 tasks",HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update-task/{taskId}")  // update task status by the creator
    public ResponseEntity<?> updateTask(@PathVariable Long taskId,@RequestParam TaskEntity.Status status,@RequestParam Long creatorUserId) {

            return taskService.updateTaskStatus(taskId, status, creatorUserId);

    }

    @DeleteMapping("/delete-task/{taskId}/{userId}")  //delete task by the creator
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId, @PathVariable Long userId)
    {
        if(taskRepository.existsById(taskId))
        {
            return taskService.deleteTask(taskId,userId);
        }
        else
        {
            return new ResponseEntity<>("No Task Found or No user Found",HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/get-all-status/{assignerId}/{status}")  //show all the tasks of users by status
   public List<TaskEntity> getTasksByStatus(@PathVariable Long assignerId, @PathVariable TaskEntity.Status status)
    {
        return taskService.listAssignedTasksById(assignerId,status,null);
    }

    @GetMapping("/get-all-date/{assignerId}")  // show all the tasks of users before a date
    public List<TaskEntity> getTasksByDate(@PathVariable Long assignerId, @RequestParam LocalDate date)
    {
        return taskService.listAssignedTasksById(assignerId,null,date);

    }

    @GetMapping("/get-all-tasks/{assignerId}")  //get all the tasks of a particular user
    public List<TaskEntity> getTasksByAssignerId(@PathVariable Long assignerId)
    {
        return taskService.listAssignedTasksById(assignerId,null,null);

    }


}
