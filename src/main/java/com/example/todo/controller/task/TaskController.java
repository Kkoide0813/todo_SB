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
    // 表示処理
    //　taskEntityのid以外のすべてを表示させる
    @GetMapping("/{id}")
    public String showDetail(@PathVariable("id") long taskId, Model model){
        var taskDTO = taskService.findById(taskId)
                .map(TaskDTO::toDTO)
                .orElseThrow(TaskNotFoundException::new);
        model.addAttribute("task", taskDTO);
        return "tasks/detail";
    }

    // タスク一覧の作成ボタン　＝＞　登録フォーム
    // POST時のcreateのバリデーションエラー　＝＞　登録フォーム
    @GetMapping("/creationForm")
    public String showCreationForm(@ModelAttribute TaskForm form, Model model){
        model.addAttribute("mode", "CREATE"); // <title th:text="${mode == 'CREATE'} ? 'タスク作成' : 'タスク編集' "></title>
        return "tasks/form";
    }

    // 登録処理
    // タスク作成のPOSTリクエストで受け取ったTaskForm formをバリデーションエラーの時には、showCreationFormにformを渡す
    @PostMapping
    public String create(@Validated TaskForm form, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
           return showCreationForm(form, model);
        }
        taskService.create(form.toEntity());
        return "redirect:/tasks"; // GETの/tasksへリダイレクト
    }

    // 編集処理　タスク詳細　→　編集
    // GET /tasks/{tasikId}/editForm
    @GetMapping("/{id}/editForm")
    public String showEditForm(@PathVariable("id") long id, Model model){
        // idがDBに存在する場合は、.mapでOptionalの中身を<TaskEntity>から<TaskForm>に変更、
        // idがDBに存在しない場合、.orElseThrowで例外処理が実行
        var form = taskService.findById(id)
                .map(TaskForm::fromEntity)
                .orElseThrow(TaskNotFoundException::new);
        model.addAttribute("taskForm", form); // form.html th:object="${taskForm}"
        model.addAttribute("mode", "EDIT"); // <title th:text="${mode == 'CREATE'} ? 'タスク作成' : 'タスク編集' "></title>
        return "tasks/form";
    }

    @PutMapping("{id}") // PUT/tasks/{id}
    public String update(@PathVariable("id") long id,
                         @Validated @ModelAttribute TaskForm form, // @ModelAttributeにより、"taskForm"がキーとして自動で設定される。
                         BindingResult bindingResult,
                         Model model
    ) {
        if(bindingResult.hasErrors()){
            model.addAttribute("mode", "EDIT");
            return "tasks/form";
        }

        // ドメインクラスなので、Entityを渡したい。form -> entity
        var entity = form.toEntity(id);
        taskService.update(entity);
        return "redirect:/tasks/{id}";
    }

    // 削除処理　タスク詳細 -> タスク一覧
    // POST /tasks/1 (hidden: _method: delete)
    // -> DELETE /tasks/1 springの読み替え機能　application.properties: spring.mvc.hiddenmethod.filter.enabled=true
    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id) {
        return "redirect:/tasks";
    }



}
