package todo.todo;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;

import todo.user.User;

@Entity
@Getter
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected Todo() {
    }

    public Todo(
            String title,
            User user
    ) {
        this.title = title;
        this.completed = false;
        this.user = user;
    }

    public void update(
            String title,
            boolean completed
    ) {
        this.title = title;
        this.completed = completed;
    }
}