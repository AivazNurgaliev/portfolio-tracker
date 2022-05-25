package com.ourproject.portfoliotracker.data.stock;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    //Creating an Stock object
    //Returning persisted object
    public StockEntity addStock(StockEntity stock) {
        return stockRepository.save(stock);
    }

    //Returning a List of all Stocks
    public List<StockEntity> getAllStocks() {
        return StreamSupport
                .stream(stockRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    //Getting a Stock by id
    public StockEntity getStock(Integer id) {
        return stockRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Error! Stock not found"));
    }

    //Deleting a Stock by id
    public StockEntity deleteStock(Integer id) {
        //Validating, if the object does not exist it will throw an error
        StockEntity stock = getStock(id);
        stockRepository.delete(stock);
        return stock;
    }

    @Transactional
    public StockEntity editStock(Integer id, StockEntity stockObj) {
        StockEntity stock = getStock(id);
        stock.setPortfolioId(stockObj.getPortfolioId());
        stock.setTicker(stockObj.getTicker());
        stock.setAmount(stockObj.getAmount());

        return stock;
    }

}
