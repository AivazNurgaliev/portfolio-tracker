package com.ourproject.portfoliotracker.controllers;

import com.ourproject.portfoliotracker.exceptions.PortfolioNotFoundException;
import com.ourproject.portfoliotracker.exceptions.StockNotFoundException;
import com.ourproject.portfoliotracker.exceptions.WrongDataException;
import com.ourproject.portfoliotracker.services.PortfolioService;
import com.ourproject.portfoliotracker.dtos.StockDTO;
import com.ourproject.portfoliotracker.entities.StockEntity;
import com.ourproject.portfoliotracker.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public StockEntity addStock(@RequestBody final StockDTO stockDTO) {
        return stockService.addStock(stockDTO);
    }

    @GetMapping("/{portfolioName}/{pageId}")
    public List<StockDTO> getFirst20Stocks(Authentication authentication,
                                           @PathVariable(name = "portfolioName") String portfolioName,
                                           @PathVariable(name = "pageId") Integer pageId) {

        if (authentication == null) {
            return null;
        }

        String username = authentication.getName();
        try {
            return stockService.getFirst20Stocks(username, portfolioName, pageId);
        } catch (PortfolioNotFoundException | StockNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (WrongDataException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping
    public StockDTO getStock(Authentication authentication,
                             @RequestParam(name = "portfolioName") String portfolioName,
                             @RequestParam(name = "stockTicker") String stockTicker) {

        if (authentication == null) {
            return null;
        }

        String username = authentication.getName();
        try {
            return stockService.getStock(username, portfolioName, stockTicker);
        } catch (PortfolioNotFoundException | StockNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping
    public StockEntity deleteStock(Authentication authentication,
                                   @RequestParam(name = "portfolioName") String portfolioName,
                                   @RequestParam(name = "stockTicker") String stockTicker) {

        if (authentication == null) {
            return null;
        }

        String username = authentication.getName();
        try {
            return  stockService.deleteStock(username, portfolioName, stockTicker);
        } catch (PortfolioNotFoundException | StockNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping
    public StockDTO putStock(Authentication authentication,
                             @RequestParam(name = "portfolioName") String portfolioName,
                             @RequestParam(name = "stockTicker") String stockTicker,
                             @RequestParam(name = "newAmount") Integer newAmount) {

        if (authentication == null) {
            return null;
        }

        String username = authentication.getName();
        try {
            return stockService.editAmount(username, portfolioName, stockTicker, newAmount);
        } catch (PortfolioNotFoundException | StockNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}