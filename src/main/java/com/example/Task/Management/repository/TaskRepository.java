package com.example.Task.Management.repository;


import com.example.Task.Management.entity.TaskEntity;
import com.example.Task.Management.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByCreatedBy(UserEntity user);
    List<TaskEntity> findByAssignedUser(UserEntity user);
    List<TaskEntity> findByAssignedUserAndStatus(UserEntity user, TaskEntity.Status status);
    List<TaskEntity> findByAssignedUserAndDueDateBefore(UserEntity user, LocalDate date);

    int countByAssignedUserAndStatus(UserEntity assignedUser, TaskEntity.Status status);

}
