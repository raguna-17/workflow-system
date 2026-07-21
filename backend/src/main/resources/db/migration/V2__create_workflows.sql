CREATE TABLE workflows (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_by BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_workflow_created_by
        FOREIGN KEY (created_by)
        REFERENCES users(id)
);
CREATE TABLE workflow_steps (
    id BIGSERIAL PRIMARY KEY,
    workflow_id BIGINT NOT NULL,
    step_order INTEGER NOT NULL,
    approver_id BIGINT NOT NULL,
    CONSTRAINT fk_step_workflow
        FOREIGN KEY (workflow_id)
        REFERENCES workflows(id),
    CONSTRAINT fk_step_approver
        FOREIGN KEY (approver_id)
        REFERENCES users(id)
);
