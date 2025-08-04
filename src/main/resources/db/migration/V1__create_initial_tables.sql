CREATE TABLE users (id UUID PRIMARY KEY,
                    name VARCHAR(255),
                    email VARCHAR(255) UNIQUE,
                    password VARCHAR(255),
                    role VARCHAR(30));

CREATE TABLE tasks (task_id UUID PRIMARY KEY,
                    title VARCHAR(255) NOT NULL,
                    description TEXT,
                    status VARCHAR(30),
                    created_at TIMESTAMP NOT NULL,
                    concluded_at TIMESTAMP,
                    user_id UUID NOT NULL,
                    CONSTRAINT fk_task_user FOREIGN KEY (user_id) REFERENCES users(id));

CREATE TABLE sub_tasks (sub_task_id UUID PRIMARY KEY,
                        user_id UUID NOT NULL,
                        title VARCHAR(255) NOT NULL,
                        description TEXT,
                        status VARCHAR(30),
                        created_at TIMESTAMP NOT NULL,
                        concluded_at TIMESTAMP,
                        task_id UUID,
                        CONSTRAINT fk_subtask_user FOREIGN KEY (user_id) REFERENCES users(id),
                        CONSTRAINT fk_subtask_task FOREIGN KEY (task_id) REFERENCES tasks(task_id));

ALTER TABLE users ADD CONSTRAINT chk_email_format
    CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$');
