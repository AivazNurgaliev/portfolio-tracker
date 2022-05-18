package com.ourproject.portfoliotracker.data.repository;

import com.ourproject.portfoliotracker.data.model.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Integer> {
}
