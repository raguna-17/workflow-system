package workflow.domains.user;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;


    public UserController(
            UserService userService
    ) {
        this.userService = userService;
    }



    /**
     * ユーザー登録
     */
    @PostMapping
    public ResponseEntity<UserDto.Response> createUser(

            @Valid
            @RequestBody
            UserDto.RegisterRequest request

    ) {

        UserDto.Response response =
                userService.createUser(request);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }



    /**
     * ユーザー取得
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto.Response> getUser(

            @PathVariable Long id

    ) {

        UserDto.Response response =
                userService.getUser(id);


        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserDto.Response>> getUsers() {

        return ResponseEntity.ok(
                userService.getUsers()
        );
    }

}