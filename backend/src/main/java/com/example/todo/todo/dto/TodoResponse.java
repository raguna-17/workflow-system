package com.example.todo.todo.dto;

import com.example.todo.todo.entity.Todo;

import lombok.Getter;


@Getter
public class TodoResponse {

    private Long id;

    private String title;

    private boolean completed;


    public TodoResponse(Todo todo){

        this.id = todo.getId();
        this.title = todo.getTitle();
        this.completed = todo.isCompleted();

    }

}