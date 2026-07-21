package workflow.domains.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDto.Response createUser(
            UserDto.RegisterRequest request
    ) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException(
                    "このメールアドレスは既に登録されています"
            );
        }


        User user = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                Role.USER
        );


        User savedUser = userRepository.save(user);


        return new UserDto.Response(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }


    @Transactional(readOnly = true)
    public UserDto.Response getUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "ユーザーが存在しません"
                        )
                );


        return new UserDto.Response(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }
}