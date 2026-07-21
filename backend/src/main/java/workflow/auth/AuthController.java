package workflow.auth;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {


    private final AuthService authService;


    public AuthController(
            AuthService authService
    ) {
        this.authService = authService;
    }


    /**
     * ログイン
     */
    @PostMapping("/login")
    public ResponseEntity<AuthDto.TokenResponse> login(

            @Valid
            @RequestBody
            AuthDto.LoginRequest request

    ) {


        AuthDto.TokenResponse response =
                authService.login(request);


        return ResponseEntity.ok(response);
    }

}