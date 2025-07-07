package com.example.API.Test.Repository;

import com.example.API.Test.Model.Task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@EntityScan("com.example.API.Test.Model")
//@AutoConfigureMockMvc(addFilters = false)
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        task1 = new Task();
//        task1.setId(UUID.randomUUID());
        task1.setTaskCode("A001");
        task1.setTaskName("Setup");
        task1.setTaskDescription("This is a setup task.");
        task1.setStatusCode("03");
        task1.setStatus("In Progress");

        task2 = new Task();
//        task2.setId(UUID.randomUUID());
        task2.setTaskCode("A002");
        task2.setTaskName("Development");
        task2.setTaskDescription("This is a dev task.");
        task2.setStatusCode("03");
        task2.setStatus("In Progress");

        taskRepository.save(task1);
        taskRepository.save(task2);
    }

    @Test
    void testFindByTaskCode() {
        Task found = taskRepository.findByTaskCode("A001");
        assertNotNull(found);
        assertEquals("Setup", found.getTaskName());
    }

    @Test
    void testFindByTaskName() {
        Task found = taskRepository.findByTaskName("Development");
        assertNotNull(found);
        assertEquals("A002", found.getTaskCode());
    }

    @Test
    void testGetAllTasksByTaskCode() {
        List<Task> tasks = taskRepository.getAllTasksByTaskCode();

        assertEquals(2, tasks.size());
        assertEquals("A001", tasks.get(0).getTaskCode());
        assertEquals("A002", tasks.get(1).getTaskCode());
    }

    @Test
    void testFindById() {
        Optional<Task> found = taskRepository.findById(task1.getId());
        assertTrue(found.isPresent());
        assertEquals("Setup", found.get().getTaskName());
    }

    @Test
    void testDeleteById() {
        taskRepository.deleteById(task1.getId());
        Optional<Task> deleted = taskRepository.findById(task1.getId());
        assertFalse(deleted.isPresent());
    }
}
