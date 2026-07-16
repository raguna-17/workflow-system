package todo.todo;

import todo.todo.TodoRequest;
import todo.todo.TodoResponse;
import todo.todo.Todo;
import todo.todo.TodoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import todo.common.CustomUserDetails;
import java.util.List;


@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TodoController {


    private final TodoService service;


    // Read: 鬯ｮ・ｯ繝ｻ・ｷ鬮｣魃会ｽｽ・ｨ郢晢ｽｻ繝ｻ・ｽ郢晢ｽｻ繝ｻ・ｨ鬯ｮ・｣雎郁ｲｻ・ｽ・ｼ陞滂ｽｲ繝ｻ・ｽ繝ｻ・ｽ郢晢ｽｻ繝ｻ・ｶ鬯ｮ・ｯ繝ｻ・ｷ郢晢ｽｻ繝ｻ・ｿ鬯ｯ・ｮ繝ｻ・｢繝ｻ縺､ﾂ驛｢譎｢・ｽ・ｻ郢晢ｽｻ繝ｻ・ｾ鬩幢ｽ｢隴趣ｽ｢繝ｻ・ｽ繝ｻ・ｻ
    @GetMapping
    public List<TodoResponse> getTodos(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        return service.findAll(userDetails.getUserId());
    }

    @GetMapping("/{id}")
    public TodoResponse getTodo(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        return service.findById(
                id,
                userDetails.getUserId()
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoResponse createTodo(
            @RequestBody TodoRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        return service.create(
                request,
                userDetails.getUserId()
        );
    }

    @PutMapping("/{id}")
    public TodoResponse updateTodo(
            @PathVariable Long id,
            @RequestBody TodoRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        return service.update(
                id,
                request,
                userDetails.getUserId()
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        service.delete(
                id,
                userDetails.getUserId()
        );
    }
}
