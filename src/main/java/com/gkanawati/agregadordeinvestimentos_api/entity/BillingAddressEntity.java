package com.gkanawati.agregadordeinvestimentos_api.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "billing_address")
public class BillingAddressEntity {

  @Id
  @Column(name = "account_id")
  private UUID id;

  @OneToOne(cascade = CascadeType.ALL)
  @MapsId
  @JoinColumn(name = "account_id")
  private AccountEntity account;

  // A TABELA QUE POSSUI A FK CARREGA A ANNOTATION @JoinColumn

  @Column(name = "street")
  private String street;

  @Column(name = "number")
  private Integer number;

  public BillingAddressEntity() {
  }

  public BillingAddressEntity(UUID id, AccountEntity account, String street, Integer number) {
    this.id = id;
    this.account = account;
    this.number = number;
    this.street = street;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }
}

