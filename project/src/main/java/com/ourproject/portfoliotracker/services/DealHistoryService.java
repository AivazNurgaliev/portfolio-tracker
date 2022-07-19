package com.ourproject.portfoliotracker.services;

import com.ourproject.portfoliotracker.dtos.DealHistoryDTO;
import com.ourproject.portfoliotracker.entities.DealHistoryEntity;
import com.ourproject.portfoliotracker.exceptions.DealHistoryNotFoundException;
import com.ourproject.portfoliotracker.exceptions.WrongDataException;
import com.ourproject.portfoliotracker.repositories.DealHistoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DealHistoryService {

    private final DealHistoryRepository dealHistoryRepository;

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

    public Page<DealHistoryDTO> getFirst20Deals(Integer accountId, Integer pageId)
            throws DealHistoryNotFoundException, WrongDataException {

        Pageable pageRequest = PageRequest.of(pageId, 20);
        Page<DealHistoryEntity> dealHistoryEntities =
                dealHistoryRepository.findByAccountIdOrderByDealDateDesc(accountId, pageRequest);
        if (dealHistoryEntities == null) {
            throw new DealHistoryNotFoundException("deal History of user id: " + accountId + " not found");
        }

        ModelMapper modelMapper = new ModelMapper();
        Type dtoType = new TypeToken<DealHistoryDTO>(){}.getType();
        Page<DealHistoryDTO> dealHistoryDTOS = dealHistoryEntities.map(entity -> modelMapper.map(entity, dtoType));

        return dealHistoryDTOS;
    }

    //dealType, 0 - chosen from List, 1 - all,
    public List<DealHistoryEntity> deleteDealHistory(Integer accountId,
                                                     List<LocalDateTime> dealDatesList,
                                                     Boolean deleteAll) throws DealHistoryNotFoundException {

        List<DealHistoryEntity> dealHistoryEntityList = dealHistoryRepository.findAllByAccountId(accountId);
        if (dealHistoryEntityList == null) {
            throw new DealHistoryNotFoundException("Deal History of user id: " + accountId + " does not exist");
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
