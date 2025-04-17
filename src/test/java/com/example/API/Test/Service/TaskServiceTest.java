package com.example.API.Test.Service;

import static org.mockito.ArgumentMatchers.any;
//import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;

import com.example.API.Test.Exception.TaskNotFoundException;
import com.example.API.Test.Model.Task;
import com.example.API.Test.Repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
	@Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private UUID taskId;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);
        taskId = UUID.randomUUID();
        task = new Task();
        task.setId(taskId);
        task.setTaskCode("TASK001");
        task.setTaskName("Test Task");
        task.setTaskDescription("This is a test task.");
        task.setStatusCode("IN_PROGRESS");
        task.setStatus("In Progress");
        task.setCompleted(false);
    }

    @Test
    void testGetAllTasks() {
        List<Task> tasks = Arrays.asList(task);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();

        assertEquals(1, result.size());
        assertEquals("TASK001", result.get(0).getTaskCode());
        verify(taskRepository, times(1)).findAll();
    }

    // ✅ Test getTaskById() - Found
    @Test
    void testGetTaskById_Found() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(taskId);

        assertNotNull(result);
        assertEquals("TASK001", result.getTaskCode());
        verify(taskRepository, times(1)).findById(taskId);
    }

    // ❌ Test getTaskById() - Not Found
    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(taskId));

        verify(taskRepository, times(1)).findById(taskId);
    }

    // ✅ Test createTask()
    @Test
    void testCreateTask() {
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTask(task);

        assertNotNull(result);
        assertEquals("TASK001", result.getTaskCode());
        verify(taskRepository, times(1)).save(task);
    }

    // ✅ Test updateTask()
    @Test
    void testUpdateTask() {
        Task updatedTask = new Task();
        updatedTask.setTaskCode("TASK002");
        updatedTask.setTaskName("Updated Task");
        updatedTask.setTaskDescription("Updated description.");
        updatedTask.setStatusCode("04");
        updatedTask.setStatus("Completed");
        updatedTask.setCompleted(true);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);


        Task result = taskService.updateTask(taskId, updatedTask);

        assertEquals("TASK002", result.getTaskCode());
        assertEquals("Updated Task", result.getTaskName());
        assertEquals("04", result.getStatusCode());
        assertTrue(result.isCompleted());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(task);
    }

    // ❌ Test updateTask() - Not Found
    @Test
    void testUpdateTask_NotFound() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Task updatedTask = new Task();
        updatedTask.setTaskCode("TASK002");

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(taskId, updatedTask));

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(0)).save(any(Task.class));
    }

    // ✅ Test deleteTask() - Exists
    @Test
    void testDeleteTask_Found() {
        when(taskRepository.existsById(taskId)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(taskId);

        assertDoesNotThrow(() -> taskService.deleteTask(taskId));

        verify(taskRepository, times(1)).existsById(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    // ❌ Test deleteTask() - Not Found
    @Test
    void testDeleteTask_NotFound() {
        when(taskRepository.existsById(taskId)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(taskId));

        verify(taskRepository, times(1)).existsById(taskId);
        verify(taskRepository, times(0)).deleteById(taskId);
    }
}
