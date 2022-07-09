package com.ourproject.portfoliotracker.repositories;

import com.ourproject.portfoliotracker.entities.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Integer> {
    public List<StockEntity> findAllByPortfolioId(Integer portfolioId);
    public void deleteAllByPortfolioId(Integer portfolioId);
    public StockEntity findByPortfolioIdAndTicker(Integer portfolioId, String stockTicker);
}
