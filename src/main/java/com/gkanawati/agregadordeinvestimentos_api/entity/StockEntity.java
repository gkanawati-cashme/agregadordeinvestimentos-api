package com.gkanawati.agregadordeinvestimentos_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stocks")
public class StockEntity {

  @Id
  @Column(name = "stock_id")
  private String stockId;

  @Column(name = "description")
  private String description;

  public StockEntity() {
  }

  public StockEntity(String stockId, String description) {
    this.stockId = stockId;
    this.description = description;
  }

  public String getStockId() {
    return stockId;
  }

  public void setStockId(String stockId) {
    this.stockId = stockId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
