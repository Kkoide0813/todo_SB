package com.example.todo.controller.task;

import com.example.todo.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public String list(Model model) {
        // List<TaskEntity>　-> List<TaskDTO>に変換
        var taskList = taskService.find()
                .stream()
                .map(TaskDTO::toDTO)
                .toList();

        model.addAttribute("taskList", taskList);
        return "tasks/list";
    }

    //　taskEntityのid以外のすべてを表示させる
    @GetMapping("/{id}")
    public String showDetail(@PathVariable("id") long taskId, Model model){
        var taskEntity = taskService.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found :id = " + taskId));
        model.addAttribute("task", TaskDTO.toDTO(taskEntity)); //TaskEntityをTaskDTOに変換してビューに渡す
        return "tasks/detail";
    }

    // タスク一覧の作成ボタン　＝＞　登録フォーム
    @GetMapping("/creationForm")
    public String showCreationForm(){
        return "tasks/form";
    }

    // 登録処理
    @PostMapping
    // 入力チェック追加
    public String create(@Validated TaskForm form, BindingResult bindingResult){
        // エラーの場合＝Null違反の場合、入力画面へする
        if(bindingResult.hasErrors()){
           return "tasks/form";
        }
        taskService.create(form.toEntity());
        return "redirect:/tasks"; // GETの/tasksへリダイレクト
    }

}
