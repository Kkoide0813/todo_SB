package com.example.todo.controller.task;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskController {

    @GetMapping("/tasks")
    public String list(Model model) {
        var task = new TaskDTO(
                1L,
                "Spring Boot を学ぶ",
                "TODO アプリケーションを作ってみる",
                "TODO"
        );
        model.addAttribute("task", task);
        return "tasks/list";
    }
}
