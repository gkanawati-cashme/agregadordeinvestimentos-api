package com.gkanawati.agregadordeinvestimentos_api.service;

import com.gkanawati.agregadordeinvestimentos_api.dto.CreateStockDTO;
import com.gkanawati.agregadordeinvestimentos_api.dto.CreateUserDTO;
import com.gkanawati.agregadordeinvestimentos_api.entity.StockEntity;
import com.gkanawati.agregadordeinvestimentos_api.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

  private final StockRepository stockRepository;

  public StockService(StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  public void createStock(CreateStockDTO createStockDTO) {
    // DTO -> ENTITY
    var stockEntity = new StockEntity(createStockDTO.stockId(), createStockDTO.description());

    stockRepository.save(stockEntity);
  }
}
