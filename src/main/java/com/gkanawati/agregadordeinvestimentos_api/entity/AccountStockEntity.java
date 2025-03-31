package com.gkanawati.agregadordeinvestimentos_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts_stocks")
public class AccountStockEntity {

  @EmbeddedId
  private AccountStockId id;

  @ManyToOne
  @MapsId("accountId")
  @JoinColumn(name = "account_id")
  private AccountEntity account;

  @ManyToOne
  @MapsId("stockId")
  @JoinColumn(name = "stock_id")
  private StockEntity stock;

  @Column(name = "quantity")
  private Integer quantity;

  public AccountStockEntity() {
  }

  public AccountStockEntity(AccountStockId id, AccountEntity account, StockEntity stock, Integer quantity) {
    this.id = id;
    this.account = account;
    this.stock = stock;
    this.quantity = quantity;
  }

  public AccountStockId getId() {
    return id;
  }

  public void setId(AccountStockId id) {
    this.id = id;
  }

  public AccountEntity getAccount() {
    return account;
  }

  public void setAccount(AccountEntity account) {
    this.account = account;
  }

  public StockEntity getStock() {
    return stock;
  }

  public void setStock(StockEntity stock) {
    this.stock = stock;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

}
