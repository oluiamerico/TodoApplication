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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TaskServiceWriteTest {

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
    void testCreateTask() {
        String email = "lulalui@gmail.com";
        User user = new User();
        user.setEmail(email);

        Task task = new Task();
        task.setTitle("New Task");

        when(securityUtil.getCurrentUserEmail()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(taskRepository.save(task)).thenReturn(task);

        Task savedTask = taskService.createTask(task);

        assertEquals(user, savedTask.getUser());
    }

    @Test
    void testDeleteTask() {
        String email = "lulalui@gmail.com";
        User user = new User();
        user.setEmail(email);

        Task task = new Task();
        task.setId(1L);
        task.setUser(user);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(securityUtil.getCurrentUserEmail()).thenReturn(email);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }
}
