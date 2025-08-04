CREATE TYPE status_enum AS ENUM ('OPEN', 'IN_PROGRESS', 'CLOSED');
CREATE TYPE role_enum AS ENUM ('USER', 'ADMIN');

CREATE TABLE users (id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                    name VARCHAR(255),
                    email VARCHAR(255) UNIQUE,
                    password VARCHAR(255),
                    role role_enum);

CREATE TABLE tasks (task_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                    title VARCHAR(255) NOT NULL,
                    description TEXT,
                    status status_enum DEFAULT 'OPEN',
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    concluded_at TIMESTAMP,
                    user_id UUID NOT NULL,
                    CONSTRAINT fk_task_user FOREIGN KEY (user_id) REFERENCES users(id));

CREATE TABLE sub_tasks (sub_task_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        title VARCHAR(255) NOT NULL,
                        description TEXT,
                        status status_enum NOT NULL DEFAULT 'OPEN',
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        concluded_at TIMESTAMP,
                        task_id UUID NOT NULL,
                        CONSTRAINT fk_subtask_task FOREIGN KEY (task_id) REFERENCES tasks(task_id));

ALTER TABLE users ADD CONSTRAINT chk_email_format
    CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$');
