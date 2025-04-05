package com.example.todo.controller.task;

import com.example.todo.service.task.TaskSearchEntity;
import com.example.todo.service.task.TaskStatus;

import java.util.List;
import java.util.Optional;

/**
 * 検索フォーム
 */
public record TaskSearchForm(
        String summary,
        List<String> status
) {

    /**
     * ドメイン層　taskService.findに渡す引数の作成
     * TaskSearchEntity へ変換します。
     *
     * status リストが null または空の場合は、空のリストを設定します。
     *
     * @return TaskSearchEntity に変換された結果
     */
    public TaskSearchEntity toEntity(){
        var statusEntityList = Optional.ofNullable(status())
                .map(statusList -> statusList.stream().map(TaskStatus::valueOf).toList())
                .orElse(List.of());
        return new TaskSearchEntity(summary(), statusEntityList);
    }

}
