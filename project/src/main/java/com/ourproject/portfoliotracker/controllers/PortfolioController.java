package com.ourproject.portfoliotracker.controllers;

import com.ourproject.portfoliotracker.exceptions.DataAlreadyExistException;
import com.ourproject.portfoliotracker.exceptions.PortfolioNotFoundException;
import com.ourproject.portfoliotracker.exceptions.WrongDataException;
import com.ourproject.portfoliotracker.services.AccountService;
import com.ourproject.portfoliotracker.dtos.PortfolioDTO;
import com.ourproject.portfoliotracker.entities.PortfolioEntity;
import com.ourproject.portfoliotracker.services.PortfolioService;

import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final AccountService accountService;

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
        } catch (PortfolioNotFoundException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{pageId}")
    public Pair<Integer, List<PortfolioDTO>> getFirst20Portfolio(Authentication authentication,
                                                  @PathVariable(name = "pageId") Integer pageId) {

        if (authentication == null) {
            return null;
        }

        String userName = authentication.getName();
        try {
            Page<PortfolioDTO> portfolioDTOs = portfolioService.getFirst20Portfolio(userName, pageId);
            return Pair.of(portfolioDTOs.getTotalPages(), portfolioDTOs.getContent());
        } catch (PortfolioNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (WrongDataException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
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
        } catch (PortfolioNotFoundException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
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
        } catch (PortfolioNotFoundException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DataAlreadyExistException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
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
        } catch (PortfolioNotFoundException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}