package com.ourproject.portfoliotracker.data.accountDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/account")
public class AccountDetailsController {

    private final AccountDetailsService accountDetailsService;

    @Autowired
    public AccountDetailsController(AccountDetailsService accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
    }

    @PutMapping("/currency1")
    public AccountDetailsEntity putCurrency1(Authentication authentication,
                                             @RequestBody String currency1) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();
        AccountDetailsEntity accountDetails = accountDetailsService.editShowCurrency1(userName, currency1);
        return accountDetails;
    }

    @PutMapping("/currency2")
    public AccountDetailsEntity putCurrency2(Authentication authentication,
                                             @RequestBody String currency2) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();
        AccountDetailsEntity accountDetails = accountDetailsService.editShowCurrency1(userName, currency2);
        return accountDetails;
    }

    @PutMapping("/subscriptionDate")
    public AccountDetailsEntity putSubscriptionDate(Authentication authentication,
                                                    @RequestBody Timestamp startDate,
                                                    @RequestBody Timestamp endDate) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();
        AccountDetailsEntity accountDetails = accountDetailsService
                .editSubscriptionDate(userName, startDate, endDate);

        return accountDetails;
    }
}
