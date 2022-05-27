package com.ourproject.portfoliotracker.data.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    public AccountEntity findByUserName(String userName);
    public AccountEntity findByEmail(String email);
}
