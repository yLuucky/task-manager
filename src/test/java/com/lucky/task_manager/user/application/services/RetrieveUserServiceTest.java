package com.lucky.task_manager.user.application.services;

import com.lucky.task_manager.user.application.dtos.UsersResponseDTO;
import com.lucky.task_manager.user.application.exceptions.UserNotFoundException;
import com.lucky.task_manager.user.application.services.impl.RetrieveUserService;
import com.lucky.task_manager.user.domain.models.User;
import com.lucky.task_manager.user.domain.repositories.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RetrieveUserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private RetrieveUserService retrieveUserService;

    private UUID userId;
    private User user;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new User();
        user.setUserId(userId);
        user.setName("John Smith");
        user.setEmail("john@example.com");
    }

    @Test
    @DisplayName("Should return UsersResponseDTO when user is found by ID")
    void shouldReturnUserWhenFoundById() throws UserNotFoundException {
        Mockito.when(userRepository.findUserByUserId(userId)).thenReturn(Optional.of(user));
        UsersResponseDTO response = retrieveUserService.execute(userId);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(userId);
        assertThat(response.name()).isEqualTo("John Smith");
        assertThat(response.email()).isEqualTo("john@example.com");
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when user is not found")
    void shouldThrowExceptionWhenUserNotFound() {
        Mockito.when(userRepository.findUserByUserId(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () ->
                retrieveUserService.execute(userId)
        );
    }
}