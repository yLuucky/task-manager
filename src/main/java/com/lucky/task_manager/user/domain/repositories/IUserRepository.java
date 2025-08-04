package com.lucky.task_manager.user.domain.repositories;

import com.lucky.task_manager.user.application.dtos.UsersResponseDTO;
import com.lucky.task_manager.user.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
    Optional<User> findUserByUserId(UUID id);
}
