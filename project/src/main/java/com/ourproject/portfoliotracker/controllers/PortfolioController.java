package com.ourproject.portfoliotracker.controllers;

import com.ourproject.portfoliotracker.services.AccountService;
import com.ourproject.portfoliotracker.dtos.PortfolioDTO;
import com.ourproject.portfoliotracker.entities.PortfolioEntity;
import com.ourproject.portfoliotracker.services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final AccountService accountService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService, AccountService accountService) {
        this.portfolioService = portfolioService;
        this.accountService = accountService;
    }

    @PostMapping
    public PortfolioEntity addPortfolio(@RequestBody PortfolioDTO portfolioDTO) {
        return portfolioService.addPortfolio(portfolioDTO);
    }

    @GetMapping
    public PortfolioDTO getPortfolio(Authentication authentication,
                                     @RequestParam(name = "name") String name) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            Integer accountId = accountService.getUserId(userName);
            return portfolioService.getPortfolio(accountId, name);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @GetMapping("/{pageId}")
    public List<PortfolioDTO> getFirst20Portfolio(Authentication authentication,
                                                  @RequestParam(name = "pageId") Integer pageId) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            return portfolioService.getFirst20Portfolio(userName, pageId);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @DeleteMapping
    public PortfolioEntity deletePortfolio(Authentication authentication,
                                           @RequestParam(name = "name") String name) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            Integer accountId = accountService.getUserId(userName);
            return portfolioService.deletePortfolio(accountId, name);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @PutMapping("/name")
    public PortfolioEntity putPortfolioName(Authentication authentication,
                                            @RequestParam(name = "portfolioName") String portfolioName,
                                            @RequestParam(name = "newName") String newName) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            Integer accountId = accountService.getUserId(userName);
            return portfolioService.editName(accountId, portfolioName, newName);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @PutMapping("/description")
    public PortfolioEntity putPortfolioDescription(Authentication authentication,
                                            @RequestParam(name = "portfolioName") String portfolioName,
                                            @RequestParam(name = "newDescription") String newDescription) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            Integer accountId = accountService.getUserId(userName);
            return portfolioService.editDescription(accountId, portfolioName, newDescription);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}