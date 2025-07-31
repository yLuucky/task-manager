package com.lucky.task_manager.user.application.services.impl;

import com.lucky.task_manager.user.application.dtos.RegisterUserDTO;
import com.lucky.task_manager.user.application.exceptions.IUserExceptions;
import com.lucky.task_manager.user.application.exceptions.UserAlreadyExistsWithEmailException;
import com.lucky.task_manager.user.application.services.IRegisterUserService;
import com.lucky.task_manager.user.domain.enums.Role;
import com.lucky.task_manager.user.domain.models.User;
import com.lucky.task_manager.user.domain.repositories.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RegisterUserService implements IRegisterUserService {

    private final IUserRepository userRepository;

    @Autowired
    public RegisterUserService(final IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(final RegisterUserDTO registerUserDTO) throws UserAlreadyExistsWithEmailException {
        final Optional<User> userFound = userRepository.findByEmail(registerUserDTO.email());

        if (userFound.isPresent()) {
            throw new UserAlreadyExistsWithEmailException(IUserExceptions.USER_ALREADY_EXISTS_WITH_EMAIL_EXCEPTION_MSG);
        }

        final User user = new User();
        user.setName(registerUserDTO.name());
        user.setEmail(registerUserDTO.email());
        user.setPassword(registerUserDTO.password());
        user.setRole(Role.USER);

        userRepository.save(user);
    }
}
