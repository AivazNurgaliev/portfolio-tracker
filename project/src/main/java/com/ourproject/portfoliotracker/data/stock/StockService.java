package com.ourproject.portfoliotracker.data.stock;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public StockEntity addStock(StockDTO stockDTO) {
        StockEntity stock = new StockEntity();
        stock.setTicker(stockDTO.getTicker());
        stock.setAmount(stockDTO.getAmount());
        return stockRepository.save(stock);
    }

    //Returning a List of all Stocks
/*
    public List<StockEntity> getAllStocks() {
        return StreamSupport
                .stream(stockRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
*/

    public StockDTO getStock(Integer portfolioId, Integer stockId) {
        StockEntity stock = stockRepository.findByPortfolioIdAndStockId(portfolioId, stockId);
        if (stock == null) {
            throw new RuntimeException("Stock of portfolio id: "
                    + portfolioId + " and stock id: " + stockId + "not found!");
        }
        StockDTO stockDTO = new StockDTO();
        stockDTO.setTicker(stock.getTicker());
        stockDTO.setAmount(stock.getAmount());

        return stockDTO;
    }

    public List<StockDTO> getFirst20Stocks(Integer portfolioId, Integer pageId) {
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

/*    public List<StockEntity> deleteStocks(Integer portfolioId) {
        List<StockEntity> stockEntities = stockRepository.findAllByPortfolioId(portfolioId);
        if (stockEntities == null) {
            throw new RuntimeException("Stocks of portfolio id: " + portfolioId + " not found");
        }
        stockRepository.deleteAllByPortfolioId(portfolioId);
        return stockEntities;
    }*/

    public StockEntity deleteStock(Integer portfolioId, Integer stockId) {
        //Validating, if the object does not exist it will throw an error
        StockEntity stock = stockRepository.findByPortfolioIdAndStockId(portfolioId, stockId);
        if (stock == null) {
            throw new RuntimeException(
                    "Stock of portfolio id: " + portfolioId + " and stock id: " + stockId +  " not found"
            );
        }
        stockRepository.delete(stock);
        return stock;
    }

}
