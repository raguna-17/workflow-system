CREATE TABLE approvals (
    id BIGSERIAL PRIMARY KEY,
    request_id BIGINT NOT NULL,
    workflow_step_id BIGINT NOT NULL,
    approved_by BIGINT,
    status VARCHAR(50) NOT NULL,
    comment TEXT,
    created_at TIMESTAMP NOT NULL,
    approved_at TIMESTAMP,
    CONSTRAINT fk_approval_request
        FOREIGN KEY (request_id)
        REFERENCES requests(id),
    CONSTRAINT fk_approval_step
        FOREIGN KEY (workflow_step_id)
        REFERENCES workflow_steps(id),
    CONSTRAINT fk_approval_user
        FOREIGN KEY (approved_by)
        REFERENCES users(id)
);
