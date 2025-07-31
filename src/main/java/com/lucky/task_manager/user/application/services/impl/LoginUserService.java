package com.lucky.task_manager.user.application.services.impl;

import com.lucky.task_manager.infrastructure.security.TokenService;
import com.lucky.task_manager.user.application.dtos.AuthenticationDTO;
import com.lucky.task_manager.user.application.exceptions.IncorrectLoginInformationException;
import com.lucky.task_manager.user.application.services.ILoginUserService;
import com.lucky.task_manager.user.domain.models.User;
import com.lucky.task_manager.user.domain.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.lucky.task_manager.user.application.exceptions.IUserExceptions.INVALID_LOGIN_INFORMATION_EXCEPTION;

@Service
public class LoginUserService implements ILoginUserService {

    private final TokenService tokenService;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginUserService(final TokenService tokenService,
                            final IUserRepository userRepository,
                            final PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String execute(final AuthenticationDTO authenticationDTO) throws IncorrectLoginInformationException {
        final User user = userRepository.findByEmail(authenticationDTO.email()).orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(authenticationDTO.password(), user.getPassword())) {
            throw INVALID_LOGIN_INFORMATION_EXCEPTION;
        }
        return tokenService.generateToken(user);
    }
}
