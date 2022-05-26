package com.ourproject.portfoliotracker.data.dealHistory;

import com.ourproject.portfoliotracker.data.portfolio.PortfolioDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
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

/*    public List<DealHistoryEntity> getAllDealHistory() {
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

        return dealHistoryDTOS.subList(20 * (pageId - 1), 20 * pageId + 1)

    }

  /*  public DealHistoryEntity deleteDealHistory(Integer id) {
        DealHistoryEntity dealHistory = getDealHistory(id);
        dealHistoryRepository.delete(dealHistory);
        return dealHistory;
    }
*/
 /*   @Transactional
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
    }*/

}
