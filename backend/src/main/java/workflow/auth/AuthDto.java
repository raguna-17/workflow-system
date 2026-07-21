package workflow.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import workflow.domains.user.Role;

public class AuthDto {


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