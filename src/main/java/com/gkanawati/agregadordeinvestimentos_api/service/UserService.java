package com.gkanawati.agregadordeinvestimentos_api.service;

import com.gkanawati.agregadordeinvestimentos_api.dto.CreateUserDTO;
import com.gkanawati.agregadordeinvestimentos_api.dto.UpdateUserDTO;
import com.gkanawati.agregadordeinvestimentos_api.entity.UserEntity;
import com.gkanawati.agregadordeinvestimentos_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UUID createUser(CreateUserDTO createUserDTO) {

    // DTO -> ENTITY
    var entity = new UserEntity(
            UUID.randomUUID(),
            createUserDTO.username(),
            createUserDTO.email(),
            createUserDTO.password(),
            Instant.now(),
            null
    );

    var savedUser = userRepository.save(entity);

    return savedUser.getUserId();
  }

  public Optional<UserEntity> getUserById(String userId) {
    return userRepository.findById(UUID.fromString(userId));
  }

  public List<UserEntity> listUsers() {
    return userRepository.findAll();
  }

  public Optional<UserEntity> updateUserById(String userId, UpdateUserDTO updateUserDTO) {
    var id = UUID.fromString(userId);
    var userEntity = userRepository.findById(id);

    if (userEntity.isPresent()) {
      var user = userEntity.get();

      if (updateUserDTO.username() != null) {
        user.setUsername(updateUserDTO.username());
      }

      if (updateUserDTO.password() != null) {
        user.setPassword(updateUserDTO.password());
      }

      userRepository.save(user);
    }

    return userEntity;
  }

  public void deleteUserById(String userId) {
    var id = UUID.fromString(userId);
    var userExists = userRepository.existsById(id);

    if (userExists) {
      userRepository.deleteById(id);
    }
  }

}
