package com.luiamerico.todoapplication.service;

import com.luiamerico.todoapplication.model.Task;
import com.luiamerico.todoapplication.model.User;
import com.luiamerico.todoapplication.repository.TaskRepository;
import com.luiamerico.todoapplication.repository.UserRepository;
import com.luiamerico.todoapplication.utils.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, SecurityUtil securityUtil) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.securityUtil = securityUtil;
    }

    public List<Task> getAllTasks() {
        String email = securityUtil.getCurrentUserEmail();

        System.out.println("Tentando buscar tarefas para o email: " + email);

        if (email == null) {
            throw new RuntimeException("Nenhum usuário autenticado");
        }

        return taskRepository.findByUserEmail(email);
    }

    public Task getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        String currentUserEmail = securityUtil.getCurrentUserEmail();

        if (!task.getUser().getEmail().equals(currentUserEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para ver essa tarefa");
        }

        return task;
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));

        String currentUserEmail = securityUtil.getCurrentUserEmail();

        if (!existingTask.getUser().getEmail().equals(currentUserEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar essa tarefa");
        }

        existingTask.setTitle(taskDetails.getTitle());
        existingTask.setDescription(taskDetails.getDescription());
        existingTask.setCompleted(taskDetails.isCompleted());

        return taskRepository.save(existingTask);
    }

    public Task createTask(Task task) {
        String userEmail = securityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail);


        task.setUser(user);
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));

        String currentUserEmail = securityUtil.getCurrentUserEmail();

        if (!task.getUser().getEmail().equals(currentUserEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para excluir essa tarefa");
        }

        taskRepository.deleteById(id);
    }






}
