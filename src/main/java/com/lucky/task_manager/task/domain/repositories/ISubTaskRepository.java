package com.lucky.task_manager.task.domain.repositories;

import com.lucky.task_manager.task.domain.models.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ISubTaskRepository extends JpaRepository<SubTask, UUID> {

    List<SubTask> findByTaskId(UUID taskId);
}
