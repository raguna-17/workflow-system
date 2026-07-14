package com.example.todo.todo.service;


import com.example.todo.todo.dto.TodoRequest;
import com.example.todo.todo.dto.TodoResponse;
import com.example.todo.todo.entity.Todo;
import com.example.todo.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TodoService {


    private final TodoRepository repository;


    // READ 全件
    public List<TodoResponse> findAll(){

        return repository.findAll()
                .stream()
                .map(TodoResponse::new)
                .toList();

    }


    // READ 1件
    public TodoResponse findById(Long id){

        Todo todo = repository.findById(id)
                .orElseThrow(
                    () -> new RuntimeException("Todo not found")
                );

        return new TodoResponse(todo);

    }


    // CREATE
    public TodoResponse create(TodoRequest request){

        Todo todo = new Todo(request.getTitle());

        Todo saved = repository.save(todo);

        return new TodoResponse(saved);

    }


    // UPDATE
    public TodoResponse update(
            Long id,
            TodoRequest request
    ){

        Todo todo = repository.findById(id)
                .orElseThrow(
                    () -> new RuntimeException("Todo not found")
                );


        todo.update(
            request.getTitle(),
            request.isCompleted()
        );


        Todo saved = repository.save(todo);


        return new TodoResponse(saved);

    }


    // DELETE
    public void delete(Long id){

        repository.deleteById(id);

    }

}