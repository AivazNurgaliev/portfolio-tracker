package com.ourproject.portfoliotracker.data.portfolio;

import com.ourproject.portfoliotracker.data.account.AccountEntity;
import com.ourproject.portfoliotracker.data.account.AccountRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final AccountRepository accountRepository;
    private Integer pageSize;

    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository, AccountRepository accountRepository) {
        this.portfolioRepository = portfolioRepository;
        this.accountRepository = accountRepository;
    }

    public Integer getPortfolioId(String userName, String portfolioName) {
        /*AccountEntity account = accountRepository.findByUserName(userName);*/
        Integer accountId = accountRepository.findByUserName(userName).getAccountId();
        PortfolioEntity portfolio = portfolioRepository.findByAccountIdAndName(accountId, portfolioName);
        if (portfolio == null) {
            throw new UsernameNotFoundException("Portfolio " + portfolioName + " not found");
        }
        return portfolio.getPortfolioId();
    }

    public PortfolioEntity addPortfolio(PortfolioDTO portfolioDTO) {
        PortfolioEntity portfolio = new PortfolioEntity();
        portfolio.setName(portfolioDTO.getName());
        portfolio.setDescription(portfolioDTO.getDescription());
        return portfolioRepository.save(portfolio);
    }

    //PortfolioEntity ->
    public List<PortfolioDTO> getFirst20Portfolio(String userName, Integer pageId) {
        Integer id = accountRepository.findByUserName(userName).getAccountId();
        List<PortfolioEntity> portfolioEntities = portfolioRepository.findAllByAccountId(id);
        if (portfolioEntities == null) {
            throw new RuntimeException("Portfolios of id " + id + " not found");
        }

        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<PortfolioDTO>>(){}.getType();
        List<PortfolioDTO> portfolioDTOS = modelMapper.map(portfolioEntities, listType);

        if (pageId < 1) {
            throw new RuntimeException("pageId cannot be negative or zero: " + pageId);
        }

        return portfolioDTOS.subList(20 * (pageId - 1), 20 * pageId + 1);
    }

    //Returning a List of all portfolios
 /*   public List<PortfolioEntity> getAllPorfolios() {
        return StreamSupport
                .stream(portfolioRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }*/

    public PortfolioDTO getPortfolio(Integer accountId, String name) {
        PortfolioEntity portfolio = portfolioRepository.findByAccountIdAndName(accountId, name);
        if (portfolio == null) {
            throw new RuntimeException("Portfolio of id " + accountId + " and name " + name + " not found");
        }
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setName(portfolio.getName());
        portfolioDTO.setDescription(portfolio.getDescription());
        return portfolioDTO;
    }

    public PortfolioEntity deletePortfolio(Integer accountId, String name) {
        PortfolioEntity portfolio = portfolioRepository.findByAccountIdAndName(accountId, name);
        if (portfolio == null) {
            throw new RuntimeException("Portfolio of id " + accountId + " and name " + name + " not found");
        }
        portfolioRepository.delete(portfolio);
        return portfolio;
    }

    @Transactional
    public PortfolioEntity editName(Integer accountId, String portfolioName, String newName) {
        PortfolioEntity portfolio = portfolioRepository.findByAccountIdAndName(accountId, portfolioName);
        if (portfolio == null) {
            throw new RuntimeException("Portfolio of id " + accountId + " and name " + portfolioName + " not found");
        }
        portfolio.setName(newName);
        return portfolio;
    }

    @Transactional
    public PortfolioEntity editDescription(Integer accountId, String portfolioName, String newDescription) {
        PortfolioEntity portfolio = portfolioRepository.findByAccountIdAndName(accountId, portfolioName);
        if (portfolio == null) {
            throw new RuntimeException("Portfolio of id " + accountId + " and name " + portfolioName + " not found");
        }
        portfolio.setDescription(newDescription);
        return portfolio;
    }
}