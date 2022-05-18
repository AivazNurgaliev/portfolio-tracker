package com.ourproject.portfoliotracker.data.repository;

import com.ourproject.portfoliotracker.data.model.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Integer> {
}
