package com.example.todo.todo.controller;

import com.example.todo.todo.dto.TodoRequest;
import com.example.todo.todo.dto.TodoResponse;
import com.example.todo.todo.entity.Todo;
import com.example.todo.todo.service.TodoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {


    private final TodoService service;


    // Read: 全件取得
    @GetMapping
    public List<TodoResponse> getTodos(){

        return service.findAll();

    }


    // Read: 1件取得
    @GetMapping("/{id}")
    public TodoResponse getTodo(
            @PathVariable Long id
    ){

        return service.findById(id);

    }


    // Create: 作成
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoResponse createTodo(
            @RequestBody TodoRequest request
    ){

        return service.create(request);

    }


    // Update: 更新
    @PutMapping("/{id}")
    public TodoResponse updateTodo(
            @PathVariable Long id,
            @RequestBody TodoRequest request
    ){

        return service.update(id, request);

    }


    // Delete: 削除
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(
            @PathVariable Long id
    ){

        service.delete(id);

    }

}