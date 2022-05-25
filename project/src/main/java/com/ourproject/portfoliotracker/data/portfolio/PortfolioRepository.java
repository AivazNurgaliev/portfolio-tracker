package com.ourproject.portfoliotracker.data.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Integer> {
    public PortfolioEntity findByAccountIdAndName(Integer accountId, String name);
}
