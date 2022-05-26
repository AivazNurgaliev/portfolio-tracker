package com.ourproject.portfoliotracker.data.dealHistory;

import com.ourproject.portfoliotracker.data.dealHistory.DealHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealHistoryRepository extends JpaRepository<DealHistoryEntity, Integer> {
    public List<DealHistoryEntity> findAllByAccountId(Integer accountId);
}
