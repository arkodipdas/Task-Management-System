package com.example.Task.Management.repository;

import com.example.Task.Management.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    boolean existsByEmail(String email);

}
