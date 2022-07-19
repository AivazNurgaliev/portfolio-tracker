package com.ourproject.portfoliotracker.services;

import com.ourproject.portfoliotracker.dtos.StockDTO;
import com.ourproject.portfoliotracker.entities.StockEntity;
import com.ourproject.portfoliotracker.exceptions.PortfolioNotFoundException;
import com.ourproject.portfoliotracker.exceptions.StockNotFoundException;
import com.ourproject.portfoliotracker.exceptions.WrongDataException;
import com.ourproject.portfoliotracker.repositories.StockRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final PortfolioService portfolioService;

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

    public StockDTO getStock(String username, String portfolioName, String stockTicker)
            throws PortfolioNotFoundException, StockNotFoundException {

        Integer portfolioId = portfolioService.getPortfolioId(username, portfolioName);
        StockEntity stock = stockRepository.findByPortfolioIdAndTicker(portfolioId, stockTicker);
        if (stock == null) {
            throw new StockNotFoundException("Stock of portfolio id: "
                    + portfolioId + " and stockTicker: " + stockTicker + " not found!");
        }

        StockDTO stockDTO = new StockDTO();
        stockDTO.setTicker(stock.getTicker());
        stockDTO.setAmount(stock.getAmount());

        return stockDTO;
    }

    public Page<StockDTO> getFirst20Stocks(String username, String portfolioName, Integer pageId)
            throws PortfolioNotFoundException, StockNotFoundException, WrongDataException {

        Integer portfolioId = portfolioService.getPortfolioId(username, portfolioName);
        Pageable pageRequest = PageRequest.of(pageId, 20);
        Page<StockEntity> stockEntities = stockRepository.findByPortfolioIdOrderByTicker(portfolioId, pageRequest);
        if (stockEntities == null) {
            throw new StockNotFoundException("Stocks of portfolio id: " + portfolioId + " not found");
        }

        ModelMapper modelMapper = new ModelMapper();
        Type dtoType = new TypeToken<StockDTO>(){}.getType();
        Page<StockDTO> stockDTOS = stockEntities.map(entity -> modelMapper.map(entity, dtoType));

        return stockDTOS;
    }

    public StockEntity deleteStock(String username, String portfolioName, String stockTicker)
            throws PortfolioNotFoundException, StockNotFoundException {

        Integer portfolioId = portfolioService.getPortfolioId(username, portfolioName);
        StockEntity stock = stockRepository.findByPortfolioIdAndTicker(portfolioId, stockTicker);
        if (stock == null) {
            throw new StockNotFoundException(
                    "Stock of portfolio id: " + portfolioId + " and stock id: " + stockTicker +  " not found"
            );
        }

        stockRepository.delete(stock);

        return stock;
    }

    public StockDTO editAmount(String username, String portfolioName, String stockTicker, Integer newAmount)
            throws PortfolioNotFoundException, StockNotFoundException {

        Integer portfolioId = portfolioService.getPortfolioId(username, portfolioName);
        StockEntity stock = stockRepository.findByPortfolioIdAndTicker(portfolioId, stockTicker);
        if (stock == null) {
            throw new StockNotFoundException(
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
