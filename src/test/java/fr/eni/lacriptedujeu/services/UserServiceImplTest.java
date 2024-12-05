package fr.eni.lacriptedujeu.services;

import fr.eni.lacriptedujeu.exceptions.NotFoundException;
import fr.eni.lacriptedujeu.models.User;
import fr.eni.lacriptedujeu.repositorys.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserServiceImpl userService = new UserServiceImpl(userRepository);

    @Test
    void getById_shouldReturnUser_whenUserExists() {
        // Arrange
        User mockUser = new User();
        mockUser.setUserID(1);
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        when(userRepository.getById(1)).thenReturn(mockUser);

        // Act
        User user = userService.getById(1);

        // Assert
        assertNotNull(user);
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
    }

    @Test
    void getById_shouldThrowException_whenUserNotFound() {
        // Arrange
        when(userRepository.getById(1)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> userService.getById(1));
    }
}
