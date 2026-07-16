package todo.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import todo.user.User;
import todo.user.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtProvider jwtProvider
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("メールアドレスまたはパスワードが違います")
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("メールアドレスまたはパスワードが違います");
        }

        String accessToken =
                jwtProvider.generateAccessToken(
                        user.getEmail(),
                        user.getRole());

        String refreshToken =
                jwtProvider.generateRefreshToken(
                        user.getEmail());

        return new LoginResponse(
                accessToken,
                refreshToken
        );
    }
}