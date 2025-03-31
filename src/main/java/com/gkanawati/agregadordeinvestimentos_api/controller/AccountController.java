package com.gkanawati.agregadordeinvestimentos_api.controller;

import com.gkanawati.agregadordeinvestimentos_api.dto.AccountStockResponseDTO;
import com.gkanawati.agregadordeinvestimentos_api.dto.AssociateAccountStockDTO;
import com.gkanawati.agregadordeinvestimentos_api.dto.CreateAccountDTO;
import com.gkanawati.agregadordeinvestimentos_api.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping("/{accountId}/stocks")
  public ResponseEntity<Void> associateStock(@PathVariable String accountId, @RequestBody AssociateAccountStockDTO dto) {

    accountService.associateStock(accountId, dto);

    return ResponseEntity.ok().build();
  }


  @GetMapping("/{accountId}/stocks")
  public ResponseEntity<List<AccountStockResponseDTO>> associateStock(@PathVariable String accountId) {
    var stocks = accountService.listStocks(accountId);
    return ResponseEntity.ok(stocks);
  }

}
