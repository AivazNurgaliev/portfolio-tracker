package com.ourproject.portfoliotracker.controllers;

import com.ourproject.portfoliotracker.services.AccountService;
import com.ourproject.portfoliotracker.dtos.DealHistoryDTO;
import com.ourproject.portfoliotracker.entities.DealHistoryEntity;
import com.ourproject.portfoliotracker.services.DealHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/deal")
public class DealHistoryController {

    private final DealHistoryService dealHistoryService;
    private final AccountService accountService;

    @Autowired
    public DealHistoryController(DealHistoryService dealHistoryService, AccountService accountService) {
        this.dealHistoryService = dealHistoryService;
        this.accountService = accountService;
    }

    @PostMapping
    public DealHistoryEntity addDealHistory(@RequestBody final DealHistoryDTO dealHistoryDTO) {
        return dealHistoryService.addDealHistory(dealHistoryDTO);
    }

    @GetMapping
    public List<DealHistoryDTO> getFirst20Deals(Authentication authentication,
                                                @RequestParam(name = "pageId") Integer pageId) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            Integer accountId = accountService.getUserId(userName);
            return dealHistoryService.getFirst20Deals(accountId, pageId);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @DeleteMapping
    public List<DealHistoryEntity> deleteDealHistory(Authentication authentication,
                                                     @RequestParam(name = "dealDates") List<LocalDateTime> dealDatesList,
                                                     @RequestParam(name = "deleteAll") Boolean deleteAll) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            Integer accountId = accountService.getUserId(userName);
            return dealHistoryService.deleteDealHistory(accountId, dealDatesList, deleteAll);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}