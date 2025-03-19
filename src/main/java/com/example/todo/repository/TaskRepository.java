package com.example.todo.repository;

import com.example.todo.service.task.TaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskRepository {

    @Select("SELECT id, description, summary, status FROM tasks;")
    List<TaskEntity> select();
}
