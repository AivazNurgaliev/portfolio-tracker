package com.ourproject.portfoliotracker.controllers;

import com.ourproject.portfoliotracker.services.PortfolioService;
import com.ourproject.portfoliotracker.dtos.StockDTO;
import com.ourproject.portfoliotracker.entities.StockEntity;
import com.ourproject.portfoliotracker.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;
    private final PortfolioService portfolioService;

    @Autowired
    public StockController(StockService stockService, PortfolioService portfolioService) {
        this.stockService = stockService;
        this.portfolioService = portfolioService;
    }

    @PostMapping
    public StockEntity addStock(@RequestBody final StockDTO stockDTO) {
        return stockService.addStock(stockDTO);
    }

    @GetMapping("/byId")
    public List<StockDTO> getFirst20Stocks(Authentication authentication,
                                           @RequestParam(name = "portfolioName") String portfolioName,
                                           @RequestParam(name = "pageId") Integer pageId) {

        if (authentication == null) {
            return null;
        }

        String username = authentication.getName();
        try {
            return stockService.getFirst20Stocks(username, portfolioName, pageId);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
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
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
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
            StockDTO stockDTO = stockService.getStock(username, portfolioName, stockTicker);
            return  stockService.deleteStock(username, portfolioName, stockTicker);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
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
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}