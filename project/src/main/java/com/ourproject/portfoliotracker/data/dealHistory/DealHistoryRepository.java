package com.ourproject.portfoliotracker.data.dealHistory;

import com.ourproject.portfoliotracker.data.dealHistory.DealHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface DealHistoryRepository extends JpaRepository<DealHistoryEntity, Integer> {
    public List<DealHistoryEntity> findAllByAccountId(Integer accountId);

    // FIXME: 27.05.2022 throwing an error //cannot create a query
    // FIXME: 27.05.2022 UnsatisfiedDependencyException

    public List<DealHistoryEntity> deleteByDealDate(Timestamp dealDate);
}
