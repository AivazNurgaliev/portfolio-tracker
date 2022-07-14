package com.ourproject.portfoliotracker.repositories;

import com.ourproject.portfoliotracker.entities.DealHistoryEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DealHistoryRepository extends JpaRepository<DealHistoryEntity, Integer> {
    public List<DealHistoryEntity> findAllByAccountId(Integer accountId);
    public List<DealHistoryEntity> findByAccountIdOrderByDealDateDesc(Integer accountId, Pageable pageable);
    public List<DealHistoryEntity> deleteByAccountIdAndDealDate(Integer accountId, LocalDateTime dealDate);
}
