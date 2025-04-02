package com.example.todo.controller.task;

import com.example.todo.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    // POST時のcreateのバリデーションエラー　＝＞　登録フォーム
    @GetMapping("/creationForm")
    public String showCreationForm(@ModelAttribute TaskForm form){ // @ModelAttributeにより、modelのキー："taskForm"
        return "tasks/form";
    }

    // 登録処理
    // タスク作成のPOSTリクエストで受け取ったTaskForm formをバリデーションエラーの時には、showCreationFormにformを渡す
    @PostMapping
    public String create(@Validated TaskForm form, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
           return showCreationForm(form);
        }
        taskService.create(form.toEntity());
        return "redirect:/tasks"; // GETの/tasksへリダイレクト
    }

    // タスク詳細　→　編集
    // GET /tasks/{tasikId}/editForm
    @GetMapping("/{id}/editForm")
    public String showEditForm(@PathVariable("id") long id, Model model){
        // Optional<TaskEntity>型をTaskFormに変換する
        var taskEntity = taskService.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Task not found")); // Optionalの中身がエラーの場合、エラーメッセージ
        var form = new TaskForm(taskEntity.summary(), taskEntity.description(), taskEntity.status().name());

        model.addAttribute("taskForm", form); // form.html th:object="${taskForm}"
        return "tasks/form";
    }
}
