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
        try {
            Integer accountId = accountService.getUserId(userName);
            PortfolioDTO portfolioDTO = portfolioService.getPortfolio(accountId, name);
            return portfolioDTO;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @GetMapping("/{pageId}")
    public List<PortfolioDTO> getFirst20Portfolio(Authentication authentication,
                                                  @PathVariable(name = "pageId") Integer pageId) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();
        try {
            List<PortfolioDTO> portfolioDTOS = portfolioService.getFirst20Portfolio(userName, pageId);
            return portfolioDTOS;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @DeleteMapping
    public PortfolioEntity deletePortfolio(Authentication authentication,
                                           @RequestParam(name = "name") String name) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();
        try {
            Integer accountId = accountService.getUserId(userName);
            PortfolioEntity deletedPortfolio = portfolioService.deletePortfolio(accountId, name);
            return deletedPortfolio;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @PutMapping("/name")
    public PortfolioEntity putPortfolioName(Authentication authentication,
                                            @RequestParam(name = "portfolioName") String portfolioName,
                                            @RequestParam(name = "newName") String newName) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();
        try {
            Integer accountId = accountService.getUserId(userName);
            PortfolioEntity portfolio = portfolioService.editName(accountId, portfolioName, newName);
            return portfolio;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @PutMapping("/description")
    public PortfolioEntity putPortfolioDescription(Authentication authentication,
                                            @RequestParam(name = "portfolioName") String portfolioName,
                                            @RequestParam(name = "newDescription") String newDescription) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();
        try {
            Integer accountId = accountService.getUserId(userName);
            PortfolioEntity portfolio = portfolioService.editDescription(accountId, portfolioName, newDescription);
            return portfolio;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
