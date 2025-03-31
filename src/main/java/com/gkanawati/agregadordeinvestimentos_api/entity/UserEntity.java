package com.gkanawati.agregadordeinvestimentos_api.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID userId;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @CreationTimestamp
  private Instant creationTimestamp;

  @UpdateTimestamp
  private Instant updateTimestamp;

  @OneToMany(mappedBy = "user")
  private List<AccountEntity> accounts;

  public UserEntity() {
  }

  public UserEntity(UUID userId, String username, String email, String password, Instant creationTimestamp, Instant updateTimestamp) {
    this.userId = userId;
    this.username = username;
    this.email = email;
    this.password = password;
    this.creationTimestamp = creationTimestamp;
    this.updateTimestamp = updateTimestamp;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Instant getCreationTimestamp() {
    return creationTimestamp;
  }

  public void setCreationTimestamp(Instant creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }

  public Instant getUpdateTimestamp() {
    return updateTimestamp;
  }

  public void setUpdateTimestamp(Instant updateTimestamp) {
    this.updateTimestamp = updateTimestamp;
  }

  public List<AccountEntity> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<AccountEntity> accounts) {
    this.accounts = accounts;
  }
}
