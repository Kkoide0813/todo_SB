package com.example.todo.controller.task;

import com.example.todo.service.task.TaskEntity;
import com.example.todo.service.task.TaskService;
import com.example.todo.service.task.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/tasks")
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
    @GetMapping("/tasks/{id}")
    public String showDetail(@PathVariable("id") long taskId, Model model){
        var taskEntity = taskService.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found :id = " + taskId));
        model.addAttribute("task", TaskDTO.toDTO(taskEntity)); //TaskEntityをTaskDTOに変換してビューに渡す
        return "tasks/detail";
    }

    // GET/tasks/creationForm
    @GetMapping("/tasks/creationForm")
    public String showCreationForm(){
        return "tasks/form";
    }

    // POST/tasks
    // formからEntityを作りたい
    @PostMapping("/tasks")
    public String create(TaskForm form, Model model){

        // formからEntityを作成する
        // valueOf・・・文字列のstatusから文字列に一致するEnumクラスを取得
        var newEntity = new TaskEntity(null, form.summary(), form.description(), TaskStatus.valueOf(form.status()));

        // サービスのcreateメソッドの引数にはnew TaskEntityを渡す
        taskService.create(newEntity);
        return list(model);
    }

}
