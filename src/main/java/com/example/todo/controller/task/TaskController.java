package com.example.todo.controller.task;

import com.example.todo.service.task.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskController {

    // TaskServiceのインスタンス化
    private final TaskService taskService = new TaskService();

    @GetMapping("/tasks")
    public String list(Model model) {
        // List<TaskEntity>　-> List<TaskDTO>に変換
        var taskList = taskService.find()
                .stream()
                .map(entity -> new TaskDTO(
                        entity.id(),
                        entity.summary(),
                        entity.description(),
                        entity.status().name()
                        )
                )
                .toList();

        // 各Listを格納した処理findメソッドをtaskListでラベル化し、モデルに追加
        model.addAttribute("taskList", taskList);
        return "tasks/list";
    }
}
