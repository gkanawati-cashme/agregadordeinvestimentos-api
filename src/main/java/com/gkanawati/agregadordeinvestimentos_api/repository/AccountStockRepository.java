package com.gkanawati.agregadordeinvestimentos_api.repository;

import com.gkanawati.agregadordeinvestimentos_api.entity.AccountStockEntity;
import com.gkanawati.agregadordeinvestimentos_api.entity.AccountStockId;
import com.gkanawati.agregadordeinvestimentos_api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStockEntity, AccountStockId> {
}
