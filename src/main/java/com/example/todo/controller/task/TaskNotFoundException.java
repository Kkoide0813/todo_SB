package com.example.todo.controller.task;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) //　NOT_FOUNDにより、404エラーが返される
public class TaskNotFoundException extends RuntimeException{ // extends RuntimeExceptionをつけると例外クラスになる
}
