package com.ourproject.portfoliotracker.data.accountDetails;

import com.ourproject.portfoliotracker.data.accountDetails.AccountDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetailsEntity, Integer> {
}