package com.ourproject.portfoliotracker.data.stock;

import com.ourproject.portfoliotracker.data.portfolio.PortfolioDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public StockEntity addStock(@RequestBody final StockDTO stockDTO) {
        return stockService.addStock(stockDTO);
    }

    @GetMapping
    public List<StockDTO> getFirst20Stocks(Authentication authentication,
                                           @RequestParam(name = "portfolioId") Integer portfolioId,
                                           @RequestParam(name = "pageId") Integer pageId) {
        if (authentication == null) {
            return null;
        }
        //String userName = authentication.getName();
        List<StockDTO> stockDTOS = stockService.getFirst20Stocks(portfolioId, pageId);

        return stockDTOS;
    }

    @GetMapping
    public StockDTO getStock(Authentication authentication,
                             @RequestParam(name = "portfolioId") Integer portfolioId,
                             @RequestParam(name = "stockId") Integer stockId) {
        if (authentication == null) {
            return null;
        }
        StockDTO stockDTO = stockService.getStock(portfolioId, stockId);

        return stockDTO;
    }

    @DeleteMapping
    public List<StockEntity> deleteStocks(Authentication authentication,
                                          @RequestParam(name = "portfolioId") Integer portfolioId) {
        if (authentication == null) {
            return null;
        }
        List<StockEntity> stockEntities = stockService.deleteStocks(portfolioId);

        return stockEntities;
    }

    @DeleteMapping
    public StockEntity deleteStock(Authentication authentication,
                                   @RequestParam(name = "portfolioId") Integer portfolioId,
                                   @RequestParam(name = "stockId") Integer stockId) {
        if (authentication == null) {
            return null;
        }
        StockDTO stockDTO = stockService.getStock(portfolioId, stockId);
        if (stockDTO == null) {
            throw new RuntimeException(
                    "Portfolio id: " + portfolioId + " and stock id: " + stockId + " not found"
            );
        }
        return stockService.deleteStock(portfolioId, stockId);
    }
}
