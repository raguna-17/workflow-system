package todo.user;

public class UserResponse {

    private Long id;

    private String email;

    private Role role;


    public UserResponse(Long id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }


    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}