package com.lucky.task_manager.user.application.services.impl;

import com.lucky.task_manager.user.application.exceptions.UserNotFoundException;
import com.lucky.task_manager.user.application.services.IRetrieveUserService;
import com.lucky.task_manager.user.domain.models.User;
import com.lucky.task_manager.user.domain.repositories.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.lucky.task_manager.user.application.exceptions.IUserExceptions.USER_NOT_FOUND_EXCEPTION;

@Slf4j
@Service
public class RetrieveUserService implements IRetrieveUserService {

    private final IUserRepository userRepository;

    @Autowired
    public RetrieveUserService(final IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(final UUID id) throws UserNotFoundException {
        final Optional<User> user = userRepository.findById(id);

        return user.orElseThrow(() -> USER_NOT_FOUND_EXCEPTION);
    }
}
