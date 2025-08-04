package com.lucky.task_manager.user.controllers;

import com.lucky.task_manager.user.application.dtos.*;
import com.lucky.task_manager.user.application.exceptions.IncorrectLoginInformationException;
import com.lucky.task_manager.user.application.exceptions.UserAlreadyExistsWithEmailException;
import com.lucky.task_manager.user.application.exceptions.UserNotFoundException;
import com.lucky.task_manager.user.application.services.ILoginUserService;
import com.lucky.task_manager.user.application.services.IRegisterUserService;
import com.lucky.task_manager.user.application.services.IRetrieveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AuthenticationController implements AuthenticationApi{

    private final ILoginUserService loginUserService;
    private final IRegisterUserService registerUserService;
    private final IRetrieveUserService retrieveUserService;

    @Autowired
    public AuthenticationController(final ILoginUserService loginUserService,
                                    final IRegisterUserService registerUserService,
                                    final IRetrieveUserService retrieveUserService) {
        this.loginUserService = loginUserService;
        this.registerUserService = registerUserService;
        this.retrieveUserService = retrieveUserService;
    }

    @Override
    public ResponseEntity<Void> registerUser(RegisterUserDTO registerDTO) throws UserAlreadyExistsWithEmailException {
        registerUserService.execute(registerDTO);

        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<LoginResponseDTO> loginUser(AuthenticationDTO authenticationDTO) throws IncorrectLoginInformationException {
        final String token = loginUserService.execute(authenticationDTO);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @Override
    public ResponseEntity<UsersResponseDTO> userFound(UUID id) throws UserNotFoundException {
        final UsersResponseDTO usersResponseDTO = retrieveUserService.execute(id);

        return ResponseEntity.status(200).body(usersResponseDTO);
    }
}
