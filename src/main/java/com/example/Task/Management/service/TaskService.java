package com.example.Task.Management.service;


import com.example.Task.Management.entity.TaskEntity;
import com.example.Task.Management.entity.UserEntity;
import com.example.Task.Management.repository.TaskRepository;
import com.example.Task.Management.repository.UserRepository;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    public boolean canCreateTask(TaskEntity task)
    {
        if (task == null || task.getCreatedBy() == null)
        {
            return false;
        }
        UserEntity assignedUser = userService.getUser(task.getAssignedUser().getId());
        int pending = taskRepository.countByAssignedUserAndStatus(assignedUser, TaskEntity.Status.PENDING);

        if (pending >= 10)
        {
            log.error("Assigned User {} has more than 10 pending tasks", assignedUser.getId());
            return false;
        }

        if (assignedUser==null)
        {
            return false;
        }


        return true;
    }


    public ResponseEntity<?> updateTaskStatus(Long taskid, TaskEntity.Status newStatus, Long userId)
    {


        if (taskRepository.findById(taskid).isEmpty())
        {
            return new ResponseEntity<>("No Task Found", HttpStatus.NOT_FOUND);
        }
        else
        {
            TaskEntity task = taskRepository.findById(taskid).orElseThrow(()->new RuntimeException());
            UserEntity user = userService.getUser(userId);

            if (!user.equals(task.getAssignedUser()) && !user.equals(task.getCreatedBy()))  //only the creator can update the task status
            {
                return new ResponseEntity<>("Access Restricted",HttpStatus.FORBIDDEN);
            }
            task.setStatus(newStatus);
            task.setUpdatedBy(user);
            taskRepository.save(task);
            return new ResponseEntity<>("Task Status Updated",HttpStatus.OK);
        }
    }






    public List<TaskEntity> listAssignedTasksById(Long userId, TaskEntity.Status status, LocalDate dueBefore) // get all tasks as per the requirement
    {
        UserEntity user=userService.getUser(userId);
        if(status != null)
        {
            return taskRepository.findByAssignedUserAndStatus(user,status);
        } else if (dueBefore != null)
        {
            return taskRepository.findByAssignedUserAndDueDateBefore(user,dueBefore);
        }
        else {
            return taskRepository.findByAssignedUser(user);
        }
    }


    public  ResponseEntity<?> deleteTask(Long taskId, Long userId)
    {
        TaskEntity task=taskRepository.findById(taskId)
                .orElseThrow(()->new RuntimeException("Task Not Found"));

        UserEntity deleteUser=task.getCreatedBy();

        if(!deleteUser.getId().equals(userId))   // only the creator can delete their tasks
        {
            return new ResponseEntity<>("Only the user created the task can delete the task",HttpStatus.FORBIDDEN);
        }
        taskRepository.delete(task);
        return new ResponseEntity<>("Task Deleted by the creator",HttpStatus.OK);

    }


}
