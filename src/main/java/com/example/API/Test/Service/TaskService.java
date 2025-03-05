package com.example.API.Test.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.API.Test.Exception.TaskNotFoundException;
import com.example.API.Test.Model.Task;
import com.example.API.Test.Repository.TaskRepository;

@Service
public class TaskService {
	@Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public List<Task> getAllTasksByTaskCode() {
        return taskRepository.getAllTasksByTaskCode();
    }

    public Task getTaskById(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id " + id));
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(UUID id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id " + id));

        task.setTaskCode(taskDetails.getTaskCode());
        task.setTaskName(taskDetails.getTaskName());
        task.setTaskDescription(taskDetails.getTaskDescription());
        task.setStatusCode(taskDetails.getStatusCode());
        task.setStatus(taskDetails.getStatus());
        task.setCompleted(taskDetails.isCompleted());
        task.setDeadline(taskDetails.getDeadline());
        task.setUpdatedByUsername(taskDetails.getUpdatedByUsername());
        task.setApprovedByUsername(taskDetails.getApprovedByUsername());
        task.setApprovedAt(taskDetails.getApprovedAt());
        
        return taskRepository.save(task);
    }

    public void deleteTask(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found with id " + id);
        }
        taskRepository.deleteById(id);
    }
}
