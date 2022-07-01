package com.ourproject.portfoliotracker.data.dealHistory;

import com.ourproject.portfoliotracker.data.account.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/deal")
public class DealHistoryController {

    private final DealHistoryService dealHistoryService;
    private final AccountService accountService;

    public DealHistoryController(DealHistoryService dealHistoryService, AccountService accountService) {
        this.dealHistoryService = dealHistoryService;
        this.accountService = accountService;
    }

    @PostMapping
    public DealHistoryEntity addDealHistory(@RequestBody final DealHistoryDTO dealHistoryDTO) {
        return dealHistoryService.addDealHistory(dealHistoryDTO);
    }
//FIXME 01.07.2020: краш http://localhost:8080/deal?pageId=1
    @GetMapping
    public List<DealHistoryDTO> getFirst20Deals(Authentication authentication,
                                                @RequestParam(name = "pageId") Integer pageId) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();
        Integer accountId = accountService.getUserId(userName);

        return dealHistoryService.getFirst20Deals(accountId, pageId);
    }

    @DeleteMapping
    public List<DealHistoryEntity> deleteDealHistory(Authentication authentication,
                                                     @RequestParam(name = "dealDates") List<Timestamp> dealDatesList,
                                                     @RequestParam(name = "deleteAll") Boolean deleteAll) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();
        Integer accountId = accountService.getUserId(userName);

        return dealHistoryService.deleteDealHistory(accountId, dealDatesList, deleteAll);
    }
}
