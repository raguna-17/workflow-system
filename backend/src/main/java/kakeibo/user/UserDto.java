package kakeibo.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDto {

    /**
     * ユーザー登録リクエスト
     */
    public record RegisterRequest(

            @NotBlank
            @Email
            String email,

            @NotBlank
            String password

    ) {
    }

    /**
     * ユーザー情報レスポンス
     */
    public record Response(

            Long id,

            String email,

            Role role

    ) {
    }

}