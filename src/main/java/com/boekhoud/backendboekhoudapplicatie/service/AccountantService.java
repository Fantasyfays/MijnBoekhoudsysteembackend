// AccountantService.java
package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dto.AccountantDTO;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Accountant;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Company;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.RoleType;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.User;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.IAccountantDal;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.CompanyRepository;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountantService {

    private final IAccountantDal IAccountantDal;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountantDTO createAccountant(AccountantDTO accountantDTO, Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        User user = new User();
        user.setUsername(accountantDTO.getUsername());
        user.setPassword(passwordEncoder.encode(accountantDTO.getPassword()));

        // Stel de rol in met een enkele RoleType in plaats van een lijst
        user.setRole(RoleType.ACCOUNTANT);
        User savedUser = userRepository.save(user);

        Accountant accountant = new Accountant();
        accountant.setUser(savedUser);
        accountant.setCompany(company);
        accountant.setFirstName(accountantDTO.getFirstName());
        accountant.setLastName(accountantDTO.getLastName());
        accountant.setPhoneNumber(accountantDTO.getPhoneNumber());
        accountant.setEmail(accountantDTO.getEmail());
        accountant.setSpecialization(accountantDTO.getSpecialization());
        accountant.setOfficeLocation(accountantDTO.getOfficeLocation());
        accountant.setDateOfHire(accountantDTO.getDateOfHire());
        accountant.setActiveStatus(accountantDTO.getActiveStatus());

        Accountant savedAccountant = IAccountantDal.save(accountant);
        return convertToDTO(savedAccountant);
    }

    public List<AccountantDTO> getAllAccountants() {
        return IAccountantDal.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AccountantDTO getAccountantById(Long id) {
        Accountant accountant = IAccountantDal.findById(id)
                .orElseThrow(() -> new RuntimeException("Accountant not found"));
        return convertToDTO(accountant);
    }

    public AccountantDTO updateAccountant(Long id, AccountantDTO accountantDTO) {
        Accountant accountant = IAccountantDal.findById(id)
                .orElseThrow(() -> new RuntimeException("Accountant not found"));

        accountant.setFirstName(accountantDTO.getFirstName());
        accountant.setLastName(accountantDTO.getLastName());
        accountant.setPhoneNumber(accountantDTO.getPhoneNumber());
        accountant.setEmail(accountantDTO.getEmail());
        accountant.setSpecialization(accountantDTO.getSpecialization());
        accountant.setOfficeLocation(accountantDTO.getOfficeLocation());
        accountant.setDateOfHire(accountantDTO.getDateOfHire());
        accountant.setActiveStatus(accountantDTO.getActiveStatus());

        if (accountantDTO.getCompanyId() != null) {
            Company company = companyRepository.findById(accountantDTO.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Company not found"));
            accountant.setCompany(company);
        }

        Accountant updatedAccountant = IAccountantDal.save(accountant);
        return convertToDTO(updatedAccountant);
    }

    public void deleteAccountant(Long id) {
        IAccountantDal.deleteById(id);
    }

    private AccountantDTO convertToDTO(Accountant accountant) {
        AccountantDTO dto = new AccountantDTO();
        dto.setId(accountant.getId());
        dto.setFirstName(accountant.getFirstName());
        dto.setLastName(accountant.getLastName());
        dto.setPhoneNumber(accountant.getPhoneNumber());
        dto.setEmail(accountant.getEmail());
        dto.setSpecialization(accountant.getSpecialization());
        dto.setOfficeLocation(accountant.getOfficeLocation());
        dto.setDateOfHire(accountant.getDateOfHire());
        dto.setActiveStatus(accountant.getActiveStatus());

        if (accountant.getUser() != null) {
            dto.setUsername(accountant.getUser().getUsername());
        }
        if (accountant.getCompany() != null) {
            dto.setCompanyId(accountant.getCompany().getId());
        }

        return dto;
    }
}
