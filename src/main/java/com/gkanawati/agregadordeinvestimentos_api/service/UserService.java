package com.gkanawati.agregadordeinvestimentos_api.service;

import com.gkanawati.agregadordeinvestimentos_api.dto.AccountResponseDTO;
import com.gkanawati.agregadordeinvestimentos_api.dto.CreateAccountDTO;
import com.gkanawati.agregadordeinvestimentos_api.dto.CreateUserDTO;
import com.gkanawati.agregadordeinvestimentos_api.dto.UpdateUserDTO;
import com.gkanawati.agregadordeinvestimentos_api.entity.AccountEntity;
import com.gkanawati.agregadordeinvestimentos_api.entity.BillingAddressEntity;
import com.gkanawati.agregadordeinvestimentos_api.entity.UserEntity;
import com.gkanawati.agregadordeinvestimentos_api.repository.AccountRepository;
import com.gkanawati.agregadordeinvestimentos_api.repository.BillingAddressRepository;
import com.gkanawati.agregadordeinvestimentos_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final AccountRepository accountRepository;
  private final BillingAddressRepository billingAddressRepository;

  public UserService(UserRepository userRepository, AccountRepository accountRepository, BillingAddressRepository billingAddressRepository) {
    this.userRepository = userRepository;
    this.accountRepository = accountRepository;
    this.billingAddressRepository = billingAddressRepository;
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

  public void addAccount(String userId, CreateAccountDTO createAccountDTO) {

    var user = userRepository.findById(UUID.fromString(userId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    // DTO -> ENTITY
    var accountEntity = new AccountEntity(
            UUID.randomUUID(),
            createAccountDTO.description(),
            user,
            null,
            new ArrayList<>()
    );

    var createdAccount = accountRepository.save(accountEntity);

    var billingAddress = new BillingAddressEntity(
            createdAccount.getAccountId(),
            createdAccount,
            createAccountDTO.street(),
            createAccountDTO.number()
    );

    billingAddressRepository.save(billingAddress);
  }

  public List<AccountResponseDTO> listAccounts(String userId) {
    var user = userRepository.findById(UUID.fromString(userId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    return user.getAccounts()
            .stream()
            .map(ac -> new AccountResponseDTO(ac.getAccountId().toString(), ac.getDescription()))
            .toList();
  }
}
