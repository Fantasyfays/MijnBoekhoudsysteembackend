package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dto.AccountantDTO;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Accountant;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Company;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Role;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.User;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.AccountantDal;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.CompanyRepository;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.RoleRepository;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Lombok genereert een constructor voor final fields
public class AccountantService {

    private final AccountantDal accountantDal;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountantDTO createAccountant(AccountantDTO accountantDTO) {
        // Maak een nieuwe User aan en koppel deze aan de accountant
        User user = new User();
        user.setUsername(accountantDTO.getUsername());
        user.setPassword(passwordEncoder.encode(accountantDTO.getPassword()));

        // Koppel de rol 'Accountant' aan de gebruiker
        Role accountantRole = roleRepository.findByName("ACCOUNTANT")
                .orElseThrow(() -> new RuntimeException("Accountant role not found"));
        user.setRole(accountantRole);
        User savedUser = userRepository.save(user);

        // Haal het bedrijf op waar de accountant aan gekoppeld moet worden
        Company company = companyRepository.findById(accountantDTO.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // Maak de Accountant-entiteit aan
        Accountant accountant = new Accountant();
        accountant.setUser(savedUser);  // Koppel de User aan de Accountant
        accountant.setCompany(company);  // Koppel het Company aan de Accountant

        // Stel overige velden in
        accountant.setFirstName(accountantDTO.getFirstName());
        accountant.setLastName(accountantDTO.getLastName());
        accountant.setPhoneNumber(accountantDTO.getPhoneNumber());
        accountant.setEmail(accountantDTO.getEmail());
        accountant.setSpecialization(accountantDTO.getSpecialization());
        accountant.setOfficeLocation(accountantDTO.getOfficeLocation());
        accountant.setDateOfHire(accountantDTO.getDateOfHire());
        accountant.setActiveStatus(accountantDTO.getActiveStatus());

        Accountant savedAccountant = accountantDal.save(accountant);
        return convertToDTO(savedAccountant);
    }

    // READ - Haal alle accountants op
    public List<AccountantDTO> getAllAccountants() {
        return accountantDal.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // READ - Haal een accountant op via ID
    public AccountantDTO getAccountantById(Long id) {
        Accountant accountant = accountantDal.findById(id)
                .orElseThrow(() -> new RuntimeException("Accountant not found"));
        return convertToDTO(accountant);
    }

    // UPDATE - Werk een bestaande accountant bij
    public AccountantDTO updateAccountant(Long id, AccountantDTO accountantDTO) {
        Accountant accountant = accountantDal.findById(id)
                .orElseThrow(() -> new RuntimeException("Accountant not found"));

        accountant.setFirstName(accountantDTO.getFirstName());
        accountant.setLastName(accountantDTO.getLastName());
        accountant.setPhoneNumber(accountantDTO.getPhoneNumber());
        accountant.setEmail(accountantDTO.getEmail());
        accountant.setSpecialization(accountantDTO.getSpecialization());
        accountant.setOfficeLocation(accountantDTO.getOfficeLocation());
        accountant.setDateOfHire(accountantDTO.getDateOfHire());
        accountant.setActiveStatus(accountantDTO.getActiveStatus());

        // Als companyId is meegegeven, werk het bedrijf bij
        if (accountantDTO.getCompanyId() != null) {
            Company company = companyRepository.findById(accountantDTO.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Company not found"));
            accountant.setCompany(company);
        }

        Accountant updatedAccountant = accountantDal.save(accountant);
        return convertToDTO(updatedAccountant);
    }

    // DELETE - Verwijder een accountant
    public void deleteAccountant(Long id) {
        accountantDal.deleteById(id);
    }

    // Helper methode om Accountant naar AccountantDTO te converteren
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
