package kakeibo.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kakeibo.user.Role;

public class AuthDto {

    /**
     * ユーザー登録
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
     * ログイン
     */
    public record LoginRequest(

            @NotBlank
            @Email
            String email,

            @NotBlank
            String password

    ) {
    }

    /**
     * ログイン成功時
     */
    public record TokenResponse(

            String accessToken,

            String refreshToken,

            Long userId,

            String email,

            Role role

    ) {
    }

    /**
     * リフレッシュトークン
     */
    public record RefreshRequest(

            @NotBlank
            String refreshToken

    ) {
    }
}