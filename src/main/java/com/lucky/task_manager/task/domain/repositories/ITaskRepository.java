package com.lucky.task_manager.task.domain.repositories;

import com.lucky.task_manager.task.domain.enums.Status;
import com.lucky.task_manager.task.domain.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ITaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByUserIdAndStatus(UUID userId, Status status);
    List<Task> findByUserId(UUID userId);
//    Optional<Task> findByIdWithSubTasks(@Param("taskId") UUID taskId);
    boolean existsByTaskIdAndUserId(UUID taskId, UUID userId);
}
