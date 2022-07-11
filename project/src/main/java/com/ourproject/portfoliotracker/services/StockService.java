package com.ourproject.portfoliotracker.services;

import com.ourproject.portfoliotracker.dtos.StockDTO;
import com.ourproject.portfoliotracker.entities.StockEntity;
import com.ourproject.portfoliotracker.repositories.StockRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final PortfolioService portfolioService;

    @Autowired
    public StockService(StockRepository stockRepository, PortfolioService portfolioService) {

        this.stockRepository = stockRepository;
        this.portfolioService = portfolioService;
    }

    public StockEntity addStock(StockDTO stockDTO) {
        StockEntity stock = new StockEntity();
        stock.setTicker(stockDTO.getTicker());
        stock.setAmount(stockDTO.getAmount());
        return stockRepository.save(stock);
    }

    public StockDTO getStock(String username, String portfolioName, String stockTicker) {

        Integer portfolioId = portfolioService.getPortfolioId(username, portfolioName);
        StockEntity stock = stockRepository.findByPortfolioIdAndTicker(portfolioId, stockTicker);
        if (stock == null) {
            throw new RuntimeException("Stock of portfolio id: "
                    + portfolioId + " and stockTicker: " + stockTicker + " not found!");
        }

        StockDTO stockDTO = new StockDTO();
        stockDTO.setTicker(stock.getTicker());
        stockDTO.setAmount(stock.getAmount());

        return stockDTO;
    }

    public List<StockDTO> getFirst20Stocks(String username, String portfolioName, Integer pageId) {

        Integer portfolioId = portfolioService.getPortfolioId(username, portfolioName);
        List<StockEntity> stockEntities = stockRepository.findAllByPortfolioId(portfolioId);
        if (stockEntities == null) {
            throw new RuntimeException("Stocks of portfolio id: " + portfolioId + " not found");
        }

        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<StockDTO>>(){}.getType();
        List<StockDTO> stockDTOS = modelMapper.map(stockEntities, listType);

        int maxAllowedPages = ((stockDTOS.size() - 1) / 20) + 1;

        if (pageId < 1 || pageId > maxAllowedPages) {
            throw new RuntimeException("pageId cannot be negative or zero or more than allowed: " + pageId);
        }

        return stockDTOS.subList(
                20 * (pageId - 1),
                Math.min(20 * pageId, stockDTOS.size())
        );
    }

    public StockEntity deleteStock(String username, String portfolioName, String stockTicker) {

        Integer portfolioId = portfolioService.getPortfolioId(username, portfolioName);
        StockEntity stock = stockRepository.findByPortfolioIdAndTicker(portfolioId, stockTicker);
        if (stock == null) {
            throw new RuntimeException(
                    "Stock of portfolio id: " + portfolioId + " and stock id: " + stockTicker +  " not found"
            );
        }

        stockRepository.delete(stock);

        return stock;
    }

    public StockDTO editAmount(String username, String portfolioName, String stockTicker, Integer newAmount) {

        Integer portfolioId = portfolioService.getPortfolioId(username, portfolioName);
        StockEntity stock = stockRepository.findByPortfolioIdAndTicker(portfolioId, stockTicker);
        if (stock == null) {
            throw new RuntimeException(
                    "Stock of portfolio id: " + portfolioId + " and stock id: " + stockTicker +  " not found"
            );
        }

        stock.setAmount(newAmount);

        stockRepository.save(stock);

        StockDTO stockDTO = new StockDTO();
        stockDTO.setAmount(stock.getAmount());
        stockDTO.setTicker(stock.getTicker());

        return stockDTO;
    }
}
