package com.ourproject.portfoliotracker.data.repository;

import com.ourproject.portfoliotracker.data.model.AccountDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetailsEntity, Integer> {
}
