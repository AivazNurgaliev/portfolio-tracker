package com.ourproject.portfoliotracker.data.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Integer> {
    public StockEntity findByPortfolioIdAndStockId(Integer portfolioId, Integer stockId);
    public List<StockEntity> findAllByPortfolioId(Integer portfolioId);
    public void deleteAllByPortfolioId(Integer portfolioId);
}
