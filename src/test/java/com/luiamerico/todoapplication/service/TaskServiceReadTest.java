package com.luiamerico.todoapplication.service;

import com.luiamerico.todoapplication.model.Task;
import com.luiamerico.todoapplication.model.User;
import com.luiamerico.todoapplication.repository.TaskRepository;
import com.luiamerico.todoapplication.repository.UserRepository;
import com.luiamerico.todoapplication.utils.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskServiceReadTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTasks() {
        String email = "lulalui@gmail.com";
        Task task = new Task();
        task.setTitle("Tarefa 1");

        when(securityUtil.getCurrentUserEmail()).thenReturn(email);
        when(taskRepository.findByUserEmail(email)).thenReturn(List.of(task));

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(1, tasks.size());
        assertEquals("Tarefa 1", tasks.get(0).getTitle());
    }

    @Test
    void testGetTaskById() {
        String email = "lulalui@gmail.com";
        User user = new User();
        user.setEmail(email);
        Task task = new Task();
        task.setId(1L);
        task.setUser(user);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(securityUtil.getCurrentUserEmail()).thenReturn(email);

        Task result = taskService.getTaskById(1L);

        assertEquals(1L, result.getId());
        assertEquals(email, result.getUser().getEmail());
    }
}
