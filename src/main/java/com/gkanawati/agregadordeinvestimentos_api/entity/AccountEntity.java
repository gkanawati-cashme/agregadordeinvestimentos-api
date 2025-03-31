package com.gkanawati.agregadordeinvestimentos_api.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class AccountEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "account_id")
  private UUID accountId;

  @Column(name = "description")
  private String description;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
  @PrimaryKeyJoinColumn
  private BillingAddressEntity billingAddress;

  @OneToMany(mappedBy = "account")
  private List<AccountStockEntity> accountStocks;

  public AccountEntity() {
  }

  public AccountEntity(UUID accountId, String description, UserEntity user, BillingAddressEntity billingAddress, List<AccountStockEntity> accountStocks) {
    this.accountId = accountId;
    this.description = description;
    this.user = user;
    this.billingAddress = billingAddress;
    this.accountStocks = accountStocks;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public void setAccountId(UUID accountId) {
    this.accountId = accountId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<AccountStockEntity> getAccountStocks() {
    return accountStocks;
  }

  public void setAccountStocks(List<AccountStockEntity> accountStocks) {
    this.accountStocks = accountStocks;
  }

  public BillingAddressEntity getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(BillingAddressEntity billingAddress) {
    this.billingAddress = billingAddress;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }
}
