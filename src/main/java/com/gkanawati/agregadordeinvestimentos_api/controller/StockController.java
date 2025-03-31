package com.gkanawati.agregadordeinvestimentos_api.controller;

import com.gkanawati.agregadordeinvestimentos_api.dto.*;
import com.gkanawati.agregadordeinvestimentos_api.entity.UserEntity;
import com.gkanawati.agregadordeinvestimentos_api.service.StockService;
import com.gkanawati.agregadordeinvestimentos_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/stocks")
public class StockController {

  private final StockService stockService;

  public StockController(StockService stockService) {
    this.stockService = stockService;
  }

  @PostMapping
  public ResponseEntity<Void> createStock(@RequestBody CreateStockDTO createStockDTO) {
    stockService.createStock(createStockDTO);
    return ResponseEntity.ok().build();
  }

}
