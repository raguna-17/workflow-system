package workflow.auth;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import workflow.security.JwtProvider;
import workflow.domains.user.User;
import workflow.domains.user.UserRepository;


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
     * ログイン処理
     */
    public AuthDto.TokenResponse login(

            AuthDto.LoginRequest request

    ) {


        User user =
                userRepository.findByEmail(request.email())

                .orElseThrow(() ->
                        new RuntimeException(
                                "メールアドレスまたはパスワードが違います"
                        )
                );



        boolean matches =
                passwordEncoder.matches(

                        request.password(),

                        user.getPassword()

                );



        if (!matches) {

            throw new RuntimeException(
                    "メールアドレスまたはパスワードが違います"
            );
        }



        String accessToken =
                jwtProvider.generateAccessToken(

                        user.getEmail(),

                        user.getRole()

                );



        String refreshToken =
                jwtProvider.generateRefreshToken(

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