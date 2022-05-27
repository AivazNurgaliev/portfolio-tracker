package com.ourproject.portfoliotracker.data.stock;

import com.ourproject.portfoliotracker.data.account.AccountRepository;
import com.ourproject.portfoliotracker.data.dealHistory.DealHistoryDTO;
import com.ourproject.portfoliotracker.data.dealHistory.DealHistoryEntity;
import com.ourproject.portfoliotracker.data.portfolio.PortfolioRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final AccountRepository accountRepository;
    private final PortfolioRepository portfolioRepository;
    @Autowired
    public StockService(StockRepository stockRepository,
                        AccountRepository accountRepository,
                        PortfolioRepository portfolioRepository) {
        this.stockRepository = stockRepository;
        this.accountRepository = accountRepository;
        this.portfolioRepository = portfolioRepository;
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

        if (pageId < 1) {
            throw new RuntimeException("pageId cannot be negative or zero: " + pageId);
        }

        return stockDTOS.subList(20 * (pageId - 1), 20 * pageId + 1);
    }

    public List<StockEntity> deleteStocks(Integer portfolioId) {
        List<StockEntity> stockEntities = stockRepository.findAllByPortfolioId(portfolioId);
        if (stockEntities == null) {
            throw new RuntimeException("Stocks of portfolio id: " + portfolioId + " not found");
        }
        stockRepository.deleteAllByPortfolioId(portfolioId);
        return stockEntities;
    }

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
