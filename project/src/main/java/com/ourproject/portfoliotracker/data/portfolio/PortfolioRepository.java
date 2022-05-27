package com.ourproject.portfoliotracker.data.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Integer> {
    public PortfolioEntity findByAccountIdAndName(Integer accountId, String name);
    public List<PortfolioEntity> findAllByAccountId(Integer accountId);
}
