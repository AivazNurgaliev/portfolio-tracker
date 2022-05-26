package com.ourproject.portfoliotracker.data.portfolio;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    //Creating an portfolio object
    //Returning persisted object
    public PortfolioEntity addPortfolio(PortfolioEntity portfolio) {
        return portfolioRepository.save(portfolio);
    }

    //Returning a List of all portfolios
    public List<PortfolioEntity> getAllPorfolios() {
        return StreamSupport
                .stream(portfolioRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    //Getting a Portfolio by id
    public PortfolioEntity getPorfolio(Integer accountId, String name) {
        PortfolioEntity portfolio = portfolioRepository.findByAccountIdAndName(accountId, name);
        if (portfolio == null) {
            throw new RuntimeException("Portfolio of id " + accountId + " and name " + name + " not found");
        }
        return portfolio;
    }

    //Deleting a Portfolio by id
/*    public PortfolioEntity deletePorfolio(Integer id) {
        //Validating, if the object does not exist it will throw an error
        PortfolioEntity portfolio = getPorfolio(id);
        portfolioRepository.delete(portfolio);
        return portfolio;
    }*/

/*    @Transactional
    public PortfolioEntity editPorfolio(Integer id,PortfolioEntity portfolioObj) {
        PortfolioEntity portfolio = getPorfolio(id);
        portfolio.setAccountId(portfolioObj.getAccountId());
        portfolio.setName(portfolioObj.getName());
        portfolio.setDescription(portfolioObj.getDescription());
        return portfolio;
    }*/
}
