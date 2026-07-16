package todo.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "メールアドレス形式で入力してください")
    private String email;

    @NotBlank(message = "パスワードは必須です")
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}