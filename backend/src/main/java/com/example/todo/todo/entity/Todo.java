package com.example.todo.todo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;


@Entity
@Getter
public class Todo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;


    private boolean completed;


    protected Todo(){

    }


    public Todo(String title){

        this.title = title;
        this.completed = false;

    }


    public void update(
            String title,
            boolean completed
    ){

        this.title = title;
        this.completed = completed;

    }

}