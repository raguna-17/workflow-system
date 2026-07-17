DROP TABLE IF EXISTS todo CASCADE;

CREATE TABLE todo (
    id BIGSERIAL PRIMARY KEY,

    title VARCHAR(255),

    completed BOOLEAN NOT NULL,

    user_id BIGINT NOT NULL,

    CONSTRAINT fk_todo_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);