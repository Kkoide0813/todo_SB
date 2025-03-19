package com.example.todo.service.task;

import com.example.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    public final TaskRepository taskRepository;

    public List<TaskEntity> find() {

        return taskRepository.select();
    }

}
