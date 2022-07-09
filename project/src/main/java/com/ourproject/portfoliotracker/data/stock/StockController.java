package com.ourproject.portfoliotracker.data.stock;

import com.ourproject.portfoliotracker.data.portfolio.PortfolioService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;
    private final PortfolioService portfolioService;

    public StockController(StockService stockService, PortfolioService portfolioService) {
        this.stockService = stockService;
        this.portfolioService = portfolioService;
    }

    @PostMapping
    public StockEntity addStock(@RequestBody final StockDTO stockDTO) {
        return stockService.addStock(stockDTO);
    }
//FIXME 01.07.2022: краш http://localhost:8080/stock/byId?portfolioName=1&pageId=1
    @GetMapping("/byId")
    public List<StockDTO> getFirst20Stocks(Authentication authentication,
                                           @RequestParam(name = "portfolioName") String portfolioName,
                                           @RequestParam(name = "pageId") Integer pageId) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();
        try {
            Integer portfolioId = portfolioService.getPortfolioId(userName, portfolioName);
            List<StockDTO> stockDTOS = stockService.getFirst20Stocks(portfolioId, pageId);
            return stockDTOS;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
//FIXME 01.07.2022: краш http://localhost:8080/stock?portfolioId=7&stockId=8
    @GetMapping
    public StockDTO getStock(Authentication authentication,
                             @RequestParam(name = "portfolioId") Integer portfolioId,
                             @RequestParam(name = "stockId") Integer stockId) {
        if (authentication == null) {
            return null;
        }

        try {
            StockDTO stockDTO = stockService.getStock(portfolioId, stockId);
            return stockDTO;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    //@DeleteMapping
/*    public List<StockEntity> deleteStocks(Authentication authentication,
                                          @RequestParam(name = "portfolioId") Integer portfolioId) {
        if (authentication == null) {
            return null;
        }
        List<StockEntity> stockEntities = stockService.deleteStocks(portfolioId);

        return stockEntities;
    }*/

    // FIXME: 09.07.2022 нужен рефактор
    @DeleteMapping
    public StockEntity deleteStock(Authentication authentication,
                                   @RequestParam(name = "portfolioId") Integer portfolioId,
                                   @RequestParam(name = "stockId") Integer stockId) {
        if (authentication == null) {
            return null;
        }

        try {
            StockDTO stockDTO = stockService.getStock(portfolioId, stockId);
            if (stockDTO == null) {
                throw new RuntimeException(
                        "Portfolio id: " + portfolioId + " and stock id: " + stockId + " not found"
                );
            }
            StockEntity stock = stockService.deleteStock(portfolioId, stockId);
            return stock;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
