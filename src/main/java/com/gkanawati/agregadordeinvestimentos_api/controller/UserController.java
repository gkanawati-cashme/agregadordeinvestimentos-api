package com.gkanawati.agregadordeinvestimentos_api.controller;

import com.gkanawati.agregadordeinvestimentos_api.dto.AccountResponseDTO;
import com.gkanawati.agregadordeinvestimentos_api.dto.CreateAccountDTO;
import com.gkanawati.agregadordeinvestimentos_api.dto.CreateUserDTO;
import com.gkanawati.agregadordeinvestimentos_api.dto.UpdateUserDTO;
import com.gkanawati.agregadordeinvestimentos_api.entity.UserEntity;
import com.gkanawati.agregadordeinvestimentos_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<UserEntity> createUser(@RequestBody CreateUserDTO createUserDTO) {
    var userId = userService.createUser(createUserDTO);
    return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserEntity> getUserById(@PathVariable String userId) {
    var user = userService.getUserById(userId);

    if (user.isPresent()) {
      return ResponseEntity.ok(user.get());
    }

    return ResponseEntity.notFound().build();
  }

  @GetMapping
  public ResponseEntity<List<UserEntity>> listUsers() {
    var users = userService.listUsers();

    return ResponseEntity.ok(users);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserEntity> updateUserById(@PathVariable String userId, @RequestBody UpdateUserDTO updateUserDTO) {
    var updatedUser = userService.updateUserById(userId, updateUserDTO);

    if (updatedUser.isPresent()) {
      return ResponseEntity.ok(updatedUser.get());
    }

    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
    userService.deleteUserById(userId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{userId}/accounts")
  public ResponseEntity<Void> addAccount(@PathVariable String userId, @RequestBody CreateAccountDTO createAccountDTO) {
    userService.addAccount(userId, createAccountDTO);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{userId}/accounts")
  public ResponseEntity<List<AccountResponseDTO>> listAccounts(@PathVariable String userId) {
    var accounts = userService.listAccounts(userId);
    return ResponseEntity.ok(accounts);
  }
}
