package com.example.API.Test.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.API.Test.Model.Task;

public interface TaskRepository extends JpaRepository<Task, UUID> {
	Task findByTaskCode(String taskCode);
    Task findByTaskName(String taskName);
    
    @Query("SELECT t FROM Task t ORDER BY t.taskCode ASC")
    List<Task> getAllTasksByTaskCode();
}
