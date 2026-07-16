package com.example.todo;

import com.example.todo.todo.dto.TodoRequest;
import com.example.todo.todo.dto.TodoResponse;
import com.example.todo.todo.entity.Todo;
import com.example.todo.todo.repository.TodoRepository;
import com.example.todo.todo.service.TodoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository repository;

    @InjectMocks
    private TodoService service;

    private Todo todo;
    private TodoRequest request;

    @BeforeEach
    void setUp() {
        todo = new Todo("Spring Boot鬩幢ｽ｢繝ｻ・ｧ鬮ｮ蛹ｺ・ｨ謚ｵ・ｽ・ｴ陞溷･・ｽ｣・ｰ隰・∞・ｽ・ｽ繝ｻ・ｷ鬩搾ｽｵ繝ｻ・ｺ髯ｷ・ｷ繝ｻ・ｶ郢晢ｽｻ郢晢ｽｻ);

        request = new TodoRequest();
        request.setTitle("鬮ｫ・ｴ陷ｴ繝ｻ・ｽ・ｽ繝ｻ・ｴ鬮ｫ・ｴ郢晢ｽｻ繝ｻ・ｽ繝ｻ・ｰ鬮ｯ貅ｯ・ｼ譁舌・);
        request.setCompleted(true);
    }

    @Test
    void findAll_returnsTodoList() {

        when(repository.findAll()).thenReturn(List.of(todo));

        List<TodoResponse> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Spring Boot鬩幢ｽ｢繝ｻ・ｧ鬮ｮ蛹ｺ・ｨ謚ｵ・ｽ・ｴ陞溷･・ｽ｣・ｰ隰・∞・ｽ・ｽ繝ｻ・ｷ鬩搾ｽｵ繝ｻ・ｺ髯ｷ・ｷ繝ｻ・ｶ郢晢ｽｻ郢晢ｽｻ, result.get(0).getTitle());

        verify(repository).findAll();
    }

    @Test
    void findById_returnsTodo() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(todo));

        TodoResponse result = service.findById(1L);

        assertEquals("Spring Boot鬩幢ｽ｢繝ｻ・ｧ鬮ｮ蛹ｺ・ｨ謚ｵ・ｽ・ｴ陞溷･・ｽ｣・ｰ隰・∞・ｽ・ｽ繝ｻ・ｷ鬩搾ｽｵ繝ｻ・ｺ髯ｷ・ｷ繝ｻ・ｶ郢晢ｽｻ郢晢ｽｻ, result.getTitle());

        verify(repository).findById(1L);
    }

    @Test
    void create_savesTodo() {

        when(repository.save(any(Todo.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TodoResponse result = service.create(request);

        assertEquals("鬮ｫ・ｴ陷ｴ繝ｻ・ｽ・ｽ繝ｻ・ｴ鬮ｫ・ｴ郢晢ｽｻ繝ｻ・ｽ繝ｻ・ｰ鬮ｯ貅ｯ・ｼ譁舌・, result.getTitle());

        verify(repository).save(any(Todo.class));
    }

    @Test
    void update_updatesTodo() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(todo));

        when(repository.save(any(Todo.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TodoResponse result = service.update(1L, request);

        assertEquals("鬮ｫ・ｴ陷ｴ繝ｻ・ｽ・ｽ繝ｻ・ｴ鬮ｫ・ｴ郢晢ｽｻ繝ｻ・ｽ繝ｻ・ｰ鬮ｯ貅ｯ・ｼ譁舌・, result.getTitle());
        assertTrue(result.isCompleted());

        verify(repository).findById(1L);
        verify(repository).save(any(Todo.class));
    }

    @Test
    void delete_callsRepository() {

        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

}
