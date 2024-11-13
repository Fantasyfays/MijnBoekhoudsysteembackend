package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Accountant;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Company;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.AccountantRepository;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.CompanyRepository;
import com.boekhoud.backendboekhoudapplicatie.dto.AccountantDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.CreateAccountantDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.UpdateAccountantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountantService {

    private final AccountantRepository accountantRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public AccountantService(AccountantRepository accountantRepository, CompanyRepository companyRepository) {
        this.accountantRepository = accountantRepository;
        this.companyRepository = companyRepository;
    }

    public AccountantDTO createAccountant(CreateAccountantDTO createAccountantDTO) {
        // Convert CreateAccountantDTO directly to Accountant
        Accountant accountant = new Accountant();
        accountant.setFirstName(createAccountantDTO.getFirstName());
        accountant.setLastName(createAccountantDTO.getLastName());
        accountant.setPhoneNumber(createAccountantDTO.getPhoneNumber());
        accountant.setEmail(createAccountantDTO.getEmail());
        accountant.setSpecialization(createAccountantDTO.getSpecialization());
        accountant.setOfficeLocation(createAccountantDTO.getOfficeLocation());
        accountant.setActiveStatus(createAccountantDTO.getActiveStatus());

        // Save accountant and return AccountantDTO
        Accountant savedAccountant = accountantRepository.save(accountant);
        return new AccountantDTO(savedAccountant.getId(), savedAccountant.getFirstName(), savedAccountant.getLastName(),
                savedAccountant.getPhoneNumber(), savedAccountant.getEmail(), savedAccountant.getSpecialization(),
                savedAccountant.getOfficeLocation(), savedAccountant.getActiveStatus());
    }

    public List<AccountantDTO> getAllAccountants() {
        // Directly map list of Accountants to AccountantDTOs
        return accountantRepository.findAll().stream()
                .map(accountant -> new AccountantDTO(accountant.getId(), accountant.getFirstName(), accountant.getLastName(),
                        accountant.getPhoneNumber(), accountant.getEmail(), accountant.getSpecialization(),
                        accountant.getOfficeLocation(), accountant.getActiveStatus()))
                .collect(Collectors.toList());
    }

    public AccountantDTO getAccountantById(Long id) {
        Accountant accountant = accountantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accountant not found"));

        // Directly map to AccountantDTO
        return new AccountantDTO(accountant.getId(), accountant.getFirstName(), accountant.getLastName(),
                accountant.getPhoneNumber(), accountant.getEmail(), accountant.getSpecialization(),
                accountant.getOfficeLocation(), accountant.getActiveStatus());
    }

    public AccountantDTO updateAccountant(Long id, UpdateAccountantDTO updateAccountantDTO) {
        Accountant accountant = accountantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accountant not found"));

        // Update fields directly from UpdateAccountantDTO
        accountant.setPhoneNumber(updateAccountantDTO.getPhoneNumber());
        accountant.setEmail(updateAccountantDTO.getEmail());
        accountant.setSpecialization(updateAccountantDTO.getSpecialization());
        accountant.setOfficeLocation(updateAccountantDTO.getOfficeLocation());
        accountant.setActiveStatus(updateAccountantDTO.getActiveStatus());

        // Save updated accountant and return AccountantDTO
        Accountant updatedAccountant = accountantRepository.save(accountant);
        return new AccountantDTO(updatedAccountant.getId(), updatedAccountant.getFirstName(), updatedAccountant.getLastName(),
                updatedAccountant.getPhoneNumber(), updatedAccountant.getEmail(), updatedAccountant.getSpecialization(),
                updatedAccountant.getOfficeLocation(), updatedAccountant.getActiveStatus());
    }

    public AccountantDTO createAccountantWithinCompany(CreateAccountantDTO createAccountantDTO, Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with ID: " + companyId));

        // Convert CreateAccountantDTO directly to Accountant
        Accountant accountant = new Accountant();
        accountant.setFirstName(createAccountantDTO.getFirstName());
        accountant.setLastName(createAccountantDTO.getLastName());
        accountant.setPhoneNumber(createAccountantDTO.getPhoneNumber());
        accountant.setEmail(createAccountantDTO.getEmail());
        accountant.setSpecialization(createAccountantDTO.getSpecialization());
        accountant.setOfficeLocation(createAccountantDTO.getOfficeLocation());
        accountant.setActiveStatus(createAccountantDTO.getActiveStatus());
        accountant.setCompany(company);

        // Save accountant and return AccountantDTO
        Accountant savedAccountant = accountantRepository.save(accountant);
        return new AccountantDTO(savedAccountant.getId(), savedAccountant.getFirstName(), savedAccountant.getLastName(),
                savedAccountant.getPhoneNumber(), savedAccountant.getEmail(), savedAccountant.getSpecialization(),
                savedAccountant.getOfficeLocation(), savedAccountant.getActiveStatus());
    }

    public void deleteAccountant(Long id) {
        accountantRepository.deleteById(id);
    }
}
