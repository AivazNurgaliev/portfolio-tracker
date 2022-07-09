package com.ourproject.portfoliotracker.services;

import com.ourproject.portfoliotracker.dtos.DealHistoryDTO;
import com.ourproject.portfoliotracker.entities.DealHistoryEntity;
import com.ourproject.portfoliotracker.repositories.DealHistoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DealHistoryService {

    private final DealHistoryRepository dealHistoryRepository;

    @Autowired
    public DealHistoryService(DealHistoryRepository dealHistoryRepository) {
        this.dealHistoryRepository = dealHistoryRepository;
    }

    public DealHistoryEntity addDealHistory(DealHistoryDTO dealHistoryDTO) {

        DealHistoryEntity dealHistory = new DealHistoryEntity();
        dealHistory.setTicker(dealHistoryDTO.getTicker());
        dealHistory.setAmount(dealHistoryDTO.getAmount());
        dealHistory.setStockPrice(dealHistoryDTO.getStockPrice());
        dealHistory.setDealDate(dealHistoryDTO.getDealDate());
        dealHistory.setDealType(dealHistoryDTO.getDealType());
        dealHistory.setCurrency(dealHistoryDTO.getCurrency());

        return dealHistoryRepository.save(dealHistory);
    }

    public List<DealHistoryDTO> getFirst20Deals(Integer accountId, Integer pageId) {

        List<DealHistoryEntity> dealHistoryEntities = dealHistoryRepository.findAllByAccountId(accountId);
        if (dealHistoryEntities == null) {
            throw new RuntimeException("deal History of user id: " + accountId + " not found");
        }

        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<DealHistoryDTO>>(){}.getType();
        List<DealHistoryDTO> dealHistoryDTOS = modelMapper.map(dealHistoryEntities, listType);

        int maxAllowedPages = ((dealHistoryDTOS.size() - 1) / 20) + 1;
        if (pageId < 1 || pageId > maxAllowedPages) {
            throw new RuntimeException("pageId cannot be negative or zero or more than allowed: " + pageId);
        }

        return dealHistoryDTOS.subList(
                20 * (pageId - 1),
                Math.min(20 * pageId, dealHistoryDTOS.size())
        );
    }

    //dealType, 0 - chosen from List, 1 - all,
    public List<DealHistoryEntity> deleteDealHistory(Integer accountId,
                                                     List<LocalDateTime> dealDatesList,
                                                     Boolean deleteAll) {

        List<DealHistoryEntity> dealHistoryEntityList = dealHistoryRepository.findAllByAccountId(accountId);
        if (dealHistoryEntityList == null) {
            throw new RuntimeException("Deal History of user id: " + accountId + " does not exist");
        }
        if (deleteAll) {
            dealHistoryRepository.deleteAll(dealHistoryEntityList);
        } else {
            for (LocalDateTime dealDate: dealDatesList) {
                dealHistoryRepository.deleteByAccountIdAndDealDate(accountId, dealDate);
            }
        }

        return dealHistoryEntityList;
    }

}
