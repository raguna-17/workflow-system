CREATE TABLE requests (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    requested_by BIGINT NOT NULL,
    workflow_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_request_user
        FOREIGN KEY (requested_by)
        REFERENCES users(id),
    CONSTRAINT fk_request_workflow
        FOREIGN KEY (workflow_id)
        REFERENCES workflows(id)
);
