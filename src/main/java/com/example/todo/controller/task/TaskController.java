package com.example.todo.controller.task;

import com.example.todo.service.task.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskController {

    // フィールド
    private final TaskService taskService = new TaskService();

    @GetMapping("/tasks")
    public String list(Model model) {
        model.addAttribute("taskList", taskService.find());
        return "tasks/list";
    }
}
