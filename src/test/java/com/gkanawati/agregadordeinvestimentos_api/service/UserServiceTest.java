package com.gkanawati.agregadordeinvestimentos_api.service;

import com.gkanawati.agregadordeinvestimentos_api.dto.CreateUserDTO;
import com.gkanawati.agregadordeinvestimentos_api.dto.UpdateUserDTO;
import com.gkanawati.agregadordeinvestimentos_api.entity.UserEntity;
import com.gkanawati.agregadordeinvestimentos_api.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @Captor
  private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;

  @Captor
  private ArgumentCaptor<UUID> uuidArgumentCaptor;

  @Nested
  class createUser {

    @Test
    @DisplayName("Should create an user with success")
    void shouldCreateUser() {
      // 3 passos na funcao de test -> triple A (Arrange, Act, Assert)

      // ARRANGE
      var user = new UserEntity(UUID.randomUUID(), "username", "email@email.com", "1234", Instant.now(), null);

      // doReturn -> funcao do mockito,
      // para simular o comportamento de um metodo
      doReturn(user).when(userRepository).save(userEntityArgumentCaptor.capture());

      var input = new CreateUserDTO("username", "email@email.com", "1234");

      // ACT
      var output = userService.createUser(input);

      // ASSERT
      assertNotNull(output);

      var userCaptured = userEntityArgumentCaptor.getValue();

      assertEquals(input.username(), userCaptured.getUsername());
      assertEquals(input.email(), userCaptured.getEmail());
    }

    @Test
    @DisplayName("Should throw exception when error occurs")
    void shouldThrowExceptionWhenErrorOccurs() {
      // arrange
      doThrow(new RuntimeException()).when(userRepository).save(any());

      var input = new CreateUserDTO("username", "email@email.com", "1234");

      // act / assert
      assertThrows(RuntimeException.class, () -> userService.createUser(input));
    }

  }

  @Nested
  class getUserById {

    @Test
    @DisplayName("Should get an user by id with success when optional is present")
    void shouldGetUserByIdWhenOptionalIsPresent() {
      // arrange
      var user = new UserEntity(UUID.randomUUID(), "username", "email@email.com", "1234", Instant.now(), null);

      doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

      // act
      var output = userService.getUserById(user.getUserId().toString());

      // assert
      assertTrue(output.isPresent());
      assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Should get an user by id with success when optional is empty")
    void shouldGetUserByIdWhenOptionalIsEmpty() {
      // arrange
      var userId = UUID.randomUUID();
      doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

      // act
      var output = userService.getUserById(userId.toString());

      // assert
      assertTrue(output.isEmpty());
      assertEquals(userId, uuidArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Should throw exception when error occurs")
    void ShouldThrowExceptionWhenErrorOccurs() {
      // arrange
      doThrow(new RuntimeException()).when(userRepository).findById(any());

      // act / assert
      assertThrows(RuntimeException.class, () -> userService.getUserById(UUID.randomUUID().toString()));
    }
  }

  @Nested
  class listUsers {

    @Test
    @DisplayName("Should return all users with success")
    void shouldReturnAllUsersWithSuccess() {
      // arrange
      var user = new UserEntity(UUID.randomUUID(), "username", "email@email.com", "1234", Instant.now(), null);

      var userList = List.of(user);
      doReturn(userList).when(userRepository).findAll();

      // act
      var output = userService.listUsers();

      // assert
      assertNotNull(output);
      assertEquals(userList.size(), output.size());
    }

  }

  @Nested
  class deleteUserById {

    @Test
    @DisplayName("should delete user with success when user exists")
    void shouldDeleteUserWithSuccessWhenUserExists() {
      // arrange
      doReturn(true).when(userRepository).existsById(uuidArgumentCaptor.capture());

      doNothing().when(userRepository).deleteById(uuidArgumentCaptor.capture());

      var userId = UUID.randomUUID();

      // act
      userService.deleteUserById(userId.toString());

      // assert
      var idList = uuidArgumentCaptor.getAllValues();
      assertEquals(userId, idList.get(0));
      assertEquals(userId, idList.get(1));

      // verify -> verifica se as funcoes foram chamadas no fluxo
      verify(userRepository, times(1)).existsById(idList.get(0));
      verify(userRepository, times(1)).deleteById(idList.get(1));
    }

    @Test
    @DisplayName("should not delete user when user not exists")
    void shouldNotDeleteUserWhenUserNotExists() {
      // arrange
      doReturn(false).when(userRepository).existsById(uuidArgumentCaptor.capture());

      var userId = UUID.randomUUID();

      // act
      userService.deleteUserById(userId.toString());

      // assert
      assertEquals(userId, uuidArgumentCaptor.getValue());

      // verify -> verifica se as funcoes foram chamadas no fluxo
      verify(userRepository, times(1)).existsById(uuidArgumentCaptor.getValue());
      verify(userRepository, times(0)).deleteById(any());
    }
  }

  @Nested
  class updateUserById {

    @Test
    @DisplayName("Should update user by id with success when user exists and username and password are filled")
    void shouldUpdateUserByIdWithSuccessWhenUserExistsAndUsernameAndPasswordAreFilled() {
      // arrange
      var userId = UUID.randomUUID();
      var updateUserDTO = new UpdateUserDTO("newUsername", "newPassword");

      var actualUser = new UserEntity(userId, "username", "email@email.com", "password", Instant.now(), null);
      var updatedUser = new UserEntity(userId, updateUserDTO.username(), "email@email.com", updateUserDTO.password(), Instant.now(), null);

      doReturn(Optional.of(actualUser)).when(userRepository).findById(uuidArgumentCaptor.capture());
      doReturn(updatedUser).when(userRepository).save(userEntityArgumentCaptor.capture());

      // act
      var output = userService.updateUserById(userId.toString(), updateUserDTO);

      // assert
      var capturedUser = userEntityArgumentCaptor.getValue();

      assertTrue(output.isPresent());
      assertEquals(actualUser.getUserId(), capturedUser.getUserId());
      assertEquals(output.get().getUsername(), capturedUser.getUsername());
      assertEquals(output.get().getPassword(), capturedUser.getPassword());

      verify(userRepository, times(1)).findById(uuidArgumentCaptor.getValue());
      verify(userRepository, times(1)).save(actualUser);
    }

    @Test
    @DisplayName("Should update user by id with success when user exists and username is filled")
    void shouldNotUpdateUserWhenUserNotExists() {
      // arrange
      var userId = UUID.randomUUID();
      var updateUserDTO = new UpdateUserDTO("newUsername", "newPassword");

      doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

      // act
      var output = userService.updateUserById(userId.toString(), updateUserDTO);

      // assert
      assertTrue(output.isEmpty());
      assertEquals(userId, uuidArgumentCaptor.getValue());

      verify(userRepository, times(1)).findById(uuidArgumentCaptor.getValue());
      verify(userRepository, times(0)).save(any());
    }
  }
}