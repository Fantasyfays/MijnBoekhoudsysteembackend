package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Accountant;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Company;
import com.boekhoud.backendboekhoudapplicatie.dto.AccountantDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.UpdateAccountantDTO;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.AccountantRepository;
import com.boekhoud.backendboekhoudapplicatie.mapper.AccountantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.CompanyRepository;
import com.boekhoud.backendboekhoudapplicatie.dto.CreateAccountantDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountantService {

    private final AccountantRepository accountantRepository;
    private final CompanyRepository companyRepository;
    private final AccountantMapper accountantMapper;

    @Autowired
    public AccountantService(AccountantRepository accountantRepository, CompanyRepository companyRepository, AccountantMapper accountantMapper) {
        this.accountantRepository = accountantRepository;
        this.companyRepository = companyRepository;
        this.accountantMapper = accountantMapper;
    }

    public AccountantDTO createAccountant(AccountantDTO accountantDTO) {
        Accountant accountant = accountantMapper.toEntity(accountantDTO);
        Accountant savedAccountant = accountantRepository.save(accountant);
        return accountantMapper.toDTO(savedAccountant);
    }

    public List<AccountantDTO> getAllAccountants() {
        return accountantRepository.findAll().stream()
                .map(accountantMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AccountantDTO getAccountantById(Long id) {
        Accountant accountant = accountantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accountant not found"));
        return accountantMapper.toDTO(accountant);
    }

    public AccountantDTO updateAccountant(Long id, UpdateAccountantDTO updateAccountantDTO) {
        Accountant accountant = accountantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accountant not found"));

        accountant.setPhoneNumber(updateAccountantDTO.getPhoneNumber());
        accountant.setEmail(updateAccountantDTO.getEmail());
        accountant.setSpecialization(updateAccountantDTO.getSpecialization());
        accountant.setOfficeLocation(updateAccountantDTO.getOfficeLocation());
        accountant.setActiveStatus(updateAccountantDTO.getActiveStatus());

        Accountant updatedAccountant = accountantRepository.save(accountant);
        return accountantMapper.toDTO(updatedAccountant);
    }

    public AccountantDTO createAccountantWithinCompany(CreateAccountantDTO createAccountantDTO, Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with ID: " + companyId));

        Accountant accountant = accountantMapper.toEntity(createAccountantDTO);
        accountant.setCompany(company);

        Accountant savedAccountant = accountantRepository.save(accountant);
        return accountantMapper.toDTO(savedAccountant);
    }

    public void deleteAccountant(Long id) {
        accountantRepository.deleteById(id);
    }
}
