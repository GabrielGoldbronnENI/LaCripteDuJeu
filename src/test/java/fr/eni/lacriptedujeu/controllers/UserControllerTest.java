package fr.eni.lacriptedujeu.controllers;

import fr.eni.lacriptedujeu.controllers.UserController;
import fr.eni.lacriptedujeu.models.User;
import fr.eni.lacriptedujeu.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        UserController userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    @Test
    void getUserById_shouldReturnUser_whenUserExists() throws Exception {
        User mockUser = new User();
        mockUser.setUserID(1);
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");

        when(userService.getById(1)).thenReturn(mockUser);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", mockUser));
    }

    @Test
    void getUserById_shouldReturn404_whenUserNotFound() throws Exception {
        when(userService.getById(1)).thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error-404"));
    }
}
