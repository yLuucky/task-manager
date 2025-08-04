-- Create indexes for better performance
CREATE INDEX idx_tasks_user_id ON tasks(user_id);
CREATE INDEX idx_tasks_status ON tasks(status);
CREATE INDEX idx_tasks_created_at ON tasks(created_at);

CREATE INDEX idx_sub_tasks_user_id ON sub_tasks(user_id);
CREATE INDEX idx_sub_tasks_task_id ON sub_tasks(task_id);
CREATE INDEX idx_sub_tasks_status ON sub_tasks(status);
CREATE INDEX idx_sub_tasks_created_at ON sub_tasks(created_at);

CREATE INDEX idx_users_email ON users(email);