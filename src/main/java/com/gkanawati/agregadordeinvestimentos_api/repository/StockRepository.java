package com.gkanawati.agregadordeinvestimentos_api.repository;

import com.gkanawati.agregadordeinvestimentos_api.entity.StockEntity;
import com.gkanawati.agregadordeinvestimentos_api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, String> {
}
