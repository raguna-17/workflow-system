package todo.todo;

import todo.user.User;
import todo.user.UserRepository;
import todo.todo.TodoRequest;
import todo.todo.TodoResponse;
import todo.todo.Todo;
import todo.todo.TodoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TodoService {


    private final TodoRepository repository;
    private final UserRepository userRepository;
    
    public List<TodoResponse> findAll(Long userId) {

        return repository.findByUserId(userId)
                .stream()
                .map(TodoResponse::new)
                .toList();
    }

    public TodoResponse findById(Long id, Long userId) {

        Todo todo = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        return new TodoResponse(todo);
    }

    public TodoResponse create(
            TodoRequest request,
            Long userId
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Todo todo = new Todo(
                request.getTitle(),
                user
        );

        return new TodoResponse(repository.save(todo));
    }

    public TodoResponse update(
            Long id,
            TodoRequest request,
            Long userId
    ) {

        Todo todo = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        todo.update(
                request.getTitle(),
                request.isCompleted()
        );

        return new TodoResponse(repository.save(todo));
    }

    public void delete(Long id, Long userId) {

        Todo todo = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        repository.delete(todo);
    }
}