package com.ourproject.portfoliotracker.data.portfolio;

import com.ourproject.portfoliotracker.data.account.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final AccountService accountService;

    public PortfolioController(PortfolioService portfolioService, AccountService accountService) {
        this.portfolioService = portfolioService;
        this.accountService = accountService;
    }

    @PostMapping
    public PortfolioEntity addPortfolio(@RequestBody PortfolioDTO portfolioDTO) {
        return portfolioService.addPortfolio(portfolioDTO);
    }

    @GetMapping
    public PortfolioDTO getPortfolio(Authentication authentication,
                                     @RequestParam(name = "name") String name) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();
        Integer accountId = accountService.getUserId(userName);

        PortfolioDTO portfolioDTO = portfolioService.getPortfolio(accountId, name);

        return portfolioDTO;
    }

    @GetMapping
    public List<PortfolioDTO> getFirst20Portfolio(Authentication authentication,
                                                  @RequestParam(name = "pageId") Integer pageId) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();
        List<PortfolioDTO> portfolioDTOS = portfolioService.getFirst20Portfolio(userName, pageId);

        return portfolioDTOS;
    }

    @DeleteMapping
    public PortfolioEntity deletePortfolio(Authentication authentication,
                                           @RequestParam(name = "name") String name) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();
        Integer accountId = accountService.getUserId(userName);

        return portfolioService.deletePortfolio(accountId, name);
    }

    @PutMapping("/name")
    public PortfolioEntity putPortfolioName(Authentication authentication,
                                            @RequestParam(name = "portfolioName") String portfolioName,
                                            @RequestParam(name = "newName") String newName) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();
        Integer accountId = accountService.getUserId(userName);

        return portfolioService.editName(accountId, portfolioName, newName);
    }

    @PutMapping("/description")
    public PortfolioEntity putPortfolioDescription(Authentication authentication,
                                            @RequestParam(name = "portfolioName") String portfolioName,
                                            @RequestParam(name = "newDescription") String newDescription) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();
        Integer accountId = accountService.getUserId(userName);

        return portfolioService.editDescription(accountId, portfolioName, newDescription);
    }
}
