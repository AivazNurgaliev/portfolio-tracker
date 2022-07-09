package com.ourproject.portfoliotracker.repositories;

import com.ourproject.portfoliotracker.entities.AccountDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetailsEntity, Integer> {
}
