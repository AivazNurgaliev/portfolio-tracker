package com.ourproject.portfoliotracker.data.repository;

import com.ourproject.portfoliotracker.data.model.DealHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealHistoryRepository extends JpaRepository<DealHistoryEntity, Integer> {
}
