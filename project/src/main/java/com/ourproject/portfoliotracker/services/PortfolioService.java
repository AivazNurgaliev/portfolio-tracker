package com.ourproject.portfoliotracker.services;

import com.ourproject.portfoliotracker.entities.AccountEntity;
import com.ourproject.portfoliotracker.exceptions.DataAlreadyExistException;
import com.ourproject.portfoliotracker.exceptions.PortfolioNotFoundException;
import com.ourproject.portfoliotracker.exceptions.WrongDataException;
import com.ourproject.portfoliotracker.repositories.AccountRepository;

import com.ourproject.portfoliotracker.dtos.PortfolioDTO;
import com.ourproject.portfoliotracker.entities.PortfolioEntity;
import com.ourproject.portfoliotracker.repositories.PortfolioRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final AccountRepository accountRepository;

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

    public Page<PortfolioDTO> getFirst20Portfolio(String userName, Integer pageId)
            throws PortfolioNotFoundException, WrongDataException {

        Integer id = accountRepository.findByUserName(userName).getAccountId();
        Pageable pageRequest = PageRequest.of(pageId, 20);
        Page<PortfolioEntity> portfolioEntities = portfolioRepository.findByAccountIdOrderByNameDesc(id, pageRequest);
        if (portfolioEntities == null) {
            throw new PortfolioNotFoundException("Portfolios of id " + id + " not found");
        }

        ModelMapper modelMapper = new ModelMapper();
        Type dtoType = new TypeToken<PortfolioDTO>(){}.getType();
        Page<PortfolioDTO> portfolioDTOS = portfolioEntities.map(entity -> modelMapper.map(entity, dtoType));

        return portfolioDTOS;
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
            throws PortfolioNotFoundException, DataAlreadyExistException {

        PortfolioEntity portfolio = portfolioRepository.findByAccountIdAndName(accountId, portfolioName);
        if (portfolio == null) {
            throw new PortfolioNotFoundException(
                    "Portfolio of id " + accountId + " and name " + portfolioName + " not found"
            );
        }

        if (portfolioRepository.findByAccountIdAndName(accountId, newName) != null) {
            throw new DataAlreadyExistException("Portfolio with this name already exist: " + newName);
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
