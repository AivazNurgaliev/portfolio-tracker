package com.ourproject.portfoliotracker.data.dealHistory;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DealHistoryService {

    private final DealHistoryRepository dealHistoryRepository;

    public DealHistoryService(DealHistoryRepository dealHistoryRepository) {
        this.dealHistoryRepository = dealHistoryRepository;
    }

    //Creating an DealHistory object
    //Returning persisted object
    public DealHistoryEntity addDealHistory(DealHistoryEntity dealHistory) {
        return dealHistoryRepository.save(dealHistory);
    }

    //Returning a List of deal history
    public List<DealHistoryEntity> getAllDealHistory() {
        return StreamSupport
                .stream(dealHistoryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    //Getting a DealHistory by id
    public DealHistoryEntity getDealHistory(Integer id) {
        return dealHistoryRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Error! Deal History not found"));
    }

    //Deleting a Deal History by id
    public DealHistoryEntity deleteDealHistory(Integer id) {
        //Validating, if the object does not exist it will throw an error
        DealHistoryEntity dealHistory = getDealHistory(id);
        dealHistoryRepository.delete(dealHistory);
        return dealHistory;
    }

    @Transactional
    public DealHistoryEntity editDealHistory(Integer dealId, DealHistoryEntity dealHistoryObj) {
        //Validating, if the object does not exist it will throw an error
        DealHistoryEntity dealHistory = getDealHistory(dealId);
        dealHistory.setAccountId(dealHistoryObj.getAccountId());
        dealHistory.setTicker(dealHistoryObj.getTicker());
        dealHistory.setAmount(dealHistoryObj.getAmount());
        dealHistory.setStockPrice(dealHistoryObj.getStockPrice());
        dealHistory.setDealDate(dealHistoryObj.getDealDate());
        dealHistory.setDealType(dealHistoryObj.getDealType());
        dealHistory.setCurrency(dealHistoryObj.getCurrency());

        return dealHistory;
    }

}
