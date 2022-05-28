package com.ourproject.portfoliotracker.data.dealHistory;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.sql.Timestamp;
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

/*  public List<DealHistoryEntity> getAllDealHistory() {
        return StreamSupport
                .stream(dealHistoryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }*/

/*    public DealHistoryEntity getDealHistory(Integer id) {
        return dealHistoryRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Error! Deal History not found"));
    }*/

    public List<DealHistoryDTO> getFirst20Deals(Integer accountId, Integer pageId) {

        List<DealHistoryEntity> dealHistoryEntities = dealHistoryRepository.findAllByAccountId(accountId);
        if (dealHistoryEntities == null) {
            throw new RuntimeException("deal History of user id: " + accountId + " not found");
        }

        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<DealHistoryDTO>>(){}.getType();
        List<DealHistoryDTO> dealHistoryDTOS = modelMapper.map(dealHistoryEntities, listType);

        if (pageId < 1) {
            throw new RuntimeException("pageId cannot be negative or zero: " + pageId);
        }

        return dealHistoryDTOS.subList(20 * (pageId - 1), 20 * pageId + 1);
    }

    //dealType, 0 - chosen from List, 1 - all,
    public List<DealHistoryEntity> deleteDealHistory(Integer accountId,
                                                     List<Timestamp> dealDatesList,
                                                     Boolean deleteAll) {
        List<DealHistoryEntity> dealHistoryEntityList = dealHistoryRepository.findAllByAccountId(accountId);
        if (dealHistoryEntityList == null) {
            throw new RuntimeException("Deal History of user id: " + accountId + " does not exist");
        }
        if (deleteAll) {
            dealHistoryRepository.deleteAll(dealHistoryEntityList);
        } else {
            /*dealHistoryRepository.deleteAllByDealDate(dealDatesList);*/
            for (Timestamp dealDate: dealDatesList) {
                dealHistoryRepository.deleteByDealDate(dealDate);
            }
        }

        return dealHistoryEntityList;
    }

}
