package todo.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest {

    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "メールアドレス形式で入力してください")
    private String email;

    @NotBlank(message = "パスワードは必須です")
    @Size(min = 4, message = "パスワードは4文字以上にしてください")
    private String password;


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}