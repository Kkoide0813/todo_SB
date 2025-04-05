package com.example.todo.repository;

import com.example.todo.service.task.TaskEntity;
import com.example.todo.service.task.TaskSearchEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TaskRepository {

    @Select("""
            SELECT id, summary, description, status
            FROM tasks
            WHERE summary LIKE CONCAT('%', #{condition.summary}, '%')
            """)
    List<TaskEntity> select(@Param("condition") TaskSearchEntity condition); // condition：検索結果

    @Select("SELECT id, summary, description, status FROM tasks WHERE id = #{taskId}")
    Optional<TaskEntity> selectById(@Param("taskId") long taskId);


    @Insert("""
    INSERT INTO tasks (summary, description, status)
    VALUES (#{task.summary}, #{task.description}, #{task.status})
    """)
    void insert(@Param("task")TaskEntity newEntity);

    @Update("""
            UPDATE tasks
            SET
                summary     = #{task.summary},
                description = #{task.description},
                status      = #{task.status}
            WHERE
                id = #{task.id}
            """)
    void update(@Param("task")TaskEntity entity);

    @Delete("DELETE FROM tasks WHERE id = #{taskId}")
    void delete(@Param("taskId") long id);
}
