package com.example.API.Test.Controller;

import com.example.API.Test.Model.Task;
import com.example.API.Test.Service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private Task sampleTask;
    private UUID taskId;

    @BeforeEach
    void setUp() {
        taskId = UUID.randomUUID();
        sampleTask = new Task();
        sampleTask.setId(taskId);
        sampleTask.setTaskCode("TASK001");
        sampleTask.setTaskName("Test Task");
        sampleTask.setTaskDescription("This is a test task.");
        sampleTask.setStatusCode("03");
        sampleTask.setStatus("In Progress");
        sampleTask.setCompleted(false);
    }

    @Test
    void testGetAllTasks() throws Exception {
        List<Task> taskList = Arrays.asList(sampleTask);
        when(taskService.getAllTasksByTaskCode()).thenReturn(taskList);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Task"));
    }

    @Test
    void testGetTaskById() throws Exception {
        Mockito.when(taskService.getTaskById(taskId)).thenReturn(sampleTask);

        mockMvc.perform(get("/api/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void testCreateTask() throws Exception {
        when(taskService.createTask(any(Task.class))).thenReturn(sampleTask);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleTask)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void testUpdateTask() throws Exception {
        when(taskService.updateTask(eq(taskId), any(Task.class))).thenReturn(sampleTask);

        mockMvc.perform(put("/api/tasks/{id}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void testDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(taskId);

        mockMvc.perform(delete("/api/tasks/{id}", taskId))
                .andExpect(status().isNoContent());
    }
}

