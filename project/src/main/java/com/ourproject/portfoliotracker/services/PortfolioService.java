package com.ourproject.portfoliotracker.services;

import com.ourproject.portfoliotracker.entities.AccountEntity;
import com.ourproject.portfoliotracker.exceptions.PortfolioNotFoundException;
import com.ourproject.portfoliotracker.exceptions.WrongDataException;
import com.ourproject.portfoliotracker.repositories.AccountRepository;

import com.ourproject.portfoliotracker.dtos.PortfolioDTO;
import com.ourproject.portfoliotracker.entities.PortfolioEntity;
import com.ourproject.portfoliotracker.repositories.PortfolioRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository, AccountRepository accountRepository) {
        this.portfolioRepository = portfolioRepository;
        this.accountRepository = accountRepository;
    }

    public Integer getPortfolioId(String userName, String portfolioName)
            throws PortfolioNotFoundException {

        AccountEntity account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User not found name: " + userName);
        }

        Integer accountId = accountRepository.findByUserName(userName).getAccountId();
        PortfolioEntity portfolio = portfolioRepository.findByAccountIdAndName(accountId, portfolioName);

        if (portfolio == null) {
            throw new PortfolioNotFoundException("Portfolio " + portfolioName + " not found");
        }

        return portfolio.getPortfolioId();
    }

    public PortfolioEntity addPortfolio(PortfolioDTO portfolioDTO) {

        PortfolioEntity portfolio = new PortfolioEntity();
        portfolio.setName(portfolioDTO.getName());
        portfolio.setDescription(portfolioDTO.getDescription());

        return portfolioRepository.save(portfolio);
    }

    public List<PortfolioDTO> getFirst20Portfolio(String userName, Integer pageId)
            throws PortfolioNotFoundException, WrongDataException {

        Integer id = accountRepository.findByUserName(userName).getAccountId();
        List<PortfolioEntity> portfolioEntities = portfolioRepository.findAllByAccountId(id);
        if (portfolioEntities == null) {
            throw new PortfolioNotFoundException("Portfolios of id " + id + " not found");
        }

        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<PortfolioDTO>>(){}.getType();
        List<PortfolioDTO> portfolioDTOS = modelMapper.map(portfolioEntities, listType);

        int maxAllowedPages = ((portfolioDTOS.size() - 1) / 20) + 1;
        if (pageId < 1 || pageId > maxAllowedPages) {
            throw new WrongDataException("pageId cannot be negative or zero or more than allowed: " + pageId);
        }

        return portfolioDTOS.subList(
                20 * (pageId - 1),
                Math.min(20 * pageId, portfolioDTOS.size())
        );
    }

    public PortfolioDTO getPortfolio(Integer accountId, String name)
            throws PortfolioNotFoundException {

        PortfolioEntity portfolio = portfolioRepository.findByAccountIdAndName(accountId, name);
        if (portfolio == null) {
            throw new PortfolioNotFoundException(
                    "Portfolio of id " + accountId + " and name " + name + " not found"
            );
        }

        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setName(portfolio.getName());
        portfolioDTO.setDescription(portfolio.getDescription());

        return portfolioDTO;
    }

    public PortfolioEntity deletePortfolio(Integer accountId, String name)
            throws PortfolioNotFoundException {

        PortfolioEntity portfolio = portfolioRepository.findByAccountIdAndName(accountId, name);
        if (portfolio == null) {
            throw new PortfolioNotFoundException(
                    "Portfolio of id " + accountId + " and name " + name + " not found"
            );
        }

        portfolioRepository.delete(portfolio);

        return portfolio;
    }

    @Transactional
    public PortfolioEntity editName(Integer accountId, String portfolioName, String newName)
            throws PortfolioNotFoundException {

        PortfolioEntity portfolio = portfolioRepository.findByAccountIdAndName(accountId, portfolioName);
        if (portfolio == null) {
            throw new PortfolioNotFoundException(
                    "Portfolio of id " + accountId + " and name " + portfolioName + " not found"
            );
        }

        portfolio.setName(newName);

        return portfolio;
    }

    @Transactional
    public PortfolioEntity editDescription(Integer accountId, String portfolioName, String newDescription)
            throws PortfolioNotFoundException {

        PortfolioEntity portfolio = portfolioRepository.findByAccountIdAndName(accountId, portfolioName);
        if (portfolio == null) {
            throw new PortfolioNotFoundException(
                    "Portfolio of id " + accountId + " and name " + portfolioName + " not found"
            );
        }

        portfolio.setDescription(newDescription);

        return portfolio;
    }
}
