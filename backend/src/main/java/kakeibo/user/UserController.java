package kakeibo.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto.Response> getUser(
            @PathVariable Long id
    ) {

        UserDto.Response response = userService.getUser(id);

        return ResponseEntity.ok(response);
    }
}