package com.ourproject.portfoliotracker.data.dealHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealHistoryRepository extends JpaRepository<DealHistoryEntity, Integer> {
}
