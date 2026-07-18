package kakeibo.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kakeibo.user.Role;
import kakeibo.user.User;
import kakeibo.user.UserRepository;

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

    /**
     * ユーザー登録
     */
    public AuthDto.TokenResponse register(
            AuthDto.RegisterRequest request
    ) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("このメールアドレスは既に登録されています");
        }

        User user = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                Role.USER
        );

        User savedUser = userRepository.save(user);

        String accessToken = jwtProvider.generateAccessToken(
                savedUser.getEmail(),
                savedUser.getRole()
        );

        String refreshToken = jwtProvider.generateRefreshToken(
                savedUser.getEmail()
        );

        return new AuthDto.TokenResponse(
                accessToken,
                refreshToken,
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    /**
     * ログイン
     */
    public AuthDto.TokenResponse login(
            AuthDto.LoginRequest request
    ) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() ->
                        new RuntimeException("メールアドレスまたはパスワードが違います")
                );

        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword()
        )) {
            throw new RuntimeException("メールアドレスまたはパスワードが違います");
        }

        String accessToken = jwtProvider.generateAccessToken(
                user.getEmail(),
                user.getRole()
        );

        String refreshToken = jwtProvider.generateRefreshToken(
                user.getEmail()
        );

        return new AuthDto.TokenResponse(
                accessToken,
                refreshToken,
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }
}