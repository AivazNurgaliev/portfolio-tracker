package com.ourproject.portfoliotracker.controllers;

import com.ourproject.portfoliotracker.exceptions.DealHistoryNotFoundException;
import com.ourproject.portfoliotracker.exceptions.WrongDataException;
import com.ourproject.portfoliotracker.services.AccountService;
import com.ourproject.portfoliotracker.dtos.DealHistoryDTO;
import com.ourproject.portfoliotracker.entities.DealHistoryEntity;
import com.ourproject.portfoliotracker.services.DealHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/deal")
public class DealHistoryController {

    private final DealHistoryService dealHistoryService;
    private final AccountService accountService;

    public DealHistoryController(DealHistoryService dealHistoryService, AccountService accountService) {
        this.dealHistoryService = dealHistoryService;
        this.accountService = accountService;
    }

    @PostMapping
    public DealHistoryEntity addDealHistory(@RequestBody final DealHistoryDTO dealHistoryDTO) {
        return dealHistoryService.addDealHistory(dealHistoryDTO);
    }

    @GetMapping("/{pageId}")
    public Pair<Integer, List<DealHistoryDTO>> getFirst20Deals(Authentication authentication,
                                                @PathVariable(name = "pageId") Integer pageId) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            Integer accountId = accountService.getUserId(userName);
            Page<DealHistoryDTO> dealHistoryDTOs = dealHistoryService.getFirst20Deals(accountId, pageId);
            return Pair.of(dealHistoryDTOs.getTotalPages(), dealHistoryDTOs.getContent());
        } catch (DealHistoryNotFoundException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (WrongDataException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
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
        } catch (DealHistoryNotFoundException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}