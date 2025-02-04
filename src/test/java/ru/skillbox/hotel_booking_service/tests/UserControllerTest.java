package ru.skillbox.hotel_booking_service.tests;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ru.skillbox.hotel_booking_service.service.UserService;
import ru.skillbox.hotel_booking_service.web.controller.UserController;
import ru.skillbox.hotel_booking_service.web.model.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService userService;

  @Test
  void findAll_ShouldReturnUserList() throws Exception {
    UserListResponse userListResponse = new UserListResponse();
    given(userService.findAll(0, 10)).willReturn(userListResponse);

    mvc.perform(get("/users")
        .param("page", "0")
        .param("size", "10")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").exists());
  }

  @Test
  void findById_ShouldReturnUser() throws Exception {
    UserResponse userResponse = new UserResponse();
    given(userService.findById(1L)).willReturn(userResponse);

    mvc.perform(get("/users/1")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").exists());
  }

  @Test
  void create_ShouldReturnCreatedUser() throws Exception {

    UpsertUserRequest request = new UpsertUserRequest();
    request.setUsername("JohnDoe");
    request.setPassword("securePass123");
    request.setEmail("john.doe@example.com");

    UserResponse userResponse = new UserResponse();
    userResponse.setId(1L);
    userResponse.setUsername("JohnDoe");
    userResponse.setEmail("john.doe@example.com");

    given(userService.save(any(), any())).willReturn(userResponse);

    mvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
                {
                  "username": "JohnDoe",
                  "password": "securePass123",
                  "email": "john.doe@example.com"
                }
                """))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.username").value("JohnDoe"))
      .andExpect(jsonPath("$.email").value("john.doe@example.com"));
  }

  @Test
  void update_ShouldReturnUpdatedUser() throws Exception {

    UpdateUserRequest request = new UpdateUserRequest();
    request.setUsername("UpdatedUser");
    request.setPassword("newSecurePass");
    request.setEmail("updated.email@example.com");

    UserResponse userResponse = new UserResponse();
    userResponse.setId(1L);
    userResponse.setUsername("UpdatedUser");
    userResponse.setEmail("updated.email@example.com");

    given(userService.update(eq(1L), any(), any())).willReturn(userResponse);

    mvc.perform(put("/users/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
                {
                  "username": "UpdatedUser",
                  "password": "newSecurePass",
                  "email": "updated.email@example.com"
                }
                """))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.username").value("UpdatedUser"))
      .andExpect(jsonPath("$.email").value("updated.email@example.com"));
  }

  @Test
  void delete_ShouldReturnNoContent() throws Exception {
    willDoNothing().given(userService).deleteById(1L);

    mvc.perform(delete("/users/1")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    verify(userService, times(1)).deleteById(1L);
  }
}
