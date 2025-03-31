package com.gkanawati.agregadordeinvestimentos_api.service;

import com.gkanawati.agregadordeinvestimentos_api.dto.AccountStockResponseDTO;
import com.gkanawati.agregadordeinvestimentos_api.dto.AssociateAccountStockDTO;
import com.gkanawati.agregadordeinvestimentos_api.entity.AccountStockEntity;
import com.gkanawati.agregadordeinvestimentos_api.entity.AccountStockId;
import com.gkanawati.agregadordeinvestimentos_api.repository.AccountRepository;
import com.gkanawati.agregadordeinvestimentos_api.repository.AccountStockRepository;
import com.gkanawati.agregadordeinvestimentos_api.repository.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

  private final AccountRepository accountRepository;
  private final StockRepository stockRepository;
  private final AccountStockRepository accountStockRepository;

  public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository) {
    this.accountRepository = accountRepository;
    this.stockRepository = stockRepository;
    this.accountStockRepository = accountStockRepository;
  }


  public void associateStock(String accountId, AssociateAccountStockDTO dto) {

    var account = accountRepository.findById(UUID.fromString(accountId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    var stock = stockRepository.findById(dto.stockId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    // DTO -> ENTITY
    var id = new AccountStockId(account.getAccountId(), stock.getStockId());
    var entity = new AccountStockEntity(id, account, stock, dto.quantity());

    accountStockRepository.save(entity);
  }

  public List<AccountStockResponseDTO> listStocks(String accountId) {
    var account = accountRepository.findById(UUID.fromString(accountId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    return account.getAccountStocks()
            .stream()
            .map(as -> new AccountStockResponseDTO(as.getStock().getStockId(), as.getQuantity(), 0.0))
            .toList();
  }
}
