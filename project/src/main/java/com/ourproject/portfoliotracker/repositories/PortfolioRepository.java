package com.ourproject.portfoliotracker.repositories;

import com.ourproject.portfoliotracker.entities.PortfolioEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Integer> {
    public PortfolioEntity findByAccountIdAndName(Integer accountId, String name);
    public List<PortfolioEntity> findAllByAccountId(Integer accountId);
    public Page<PortfolioEntity> findByAccountIdOrderByNameDesc(Integer accountId, Pageable pageable);
}
