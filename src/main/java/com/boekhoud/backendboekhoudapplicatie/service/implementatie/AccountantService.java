package com.boekhoud.backendboekhoudapplicatie.service.implementatie;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Accountant;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Company;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Role;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.User;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.AccountantRepository;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.CompanyRepository;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.RoleRepository;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.UserRepository;
import com.boekhoud.backendboekhoudapplicatie.presentation.dto.AccountantDTO;
import com.boekhoud.backendboekhoudapplicatie.service.interfaces.AccountantServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountantService implements AccountantServiceInterface {

    @Autowired
    private AccountantRepository accountantRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AccountantDTO createAccountant(AccountantDTO accountantDTO, Long companyId) {
        Role role = roleRepository.findByName("ACCOUNTANT")
                .orElseThrow(() -> new RuntimeException("Accountant role not found"));

        User user = new User();
        user.setUsername(accountantDTO.getUsername());
        user.setPassword(passwordEncoder.encode(accountantDTO.getPassword()));
        user.setRole(role);

        User savedUser = userRepository.save(user);

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Accountant accountant = new Accountant();
        accountant.setUser(savedUser);
        accountant.setCompany(company);
        accountant.setFirstName(accountantDTO.getFirstName());
        accountant.setLastName(accountantDTO.getLastName());
        accountant.setEmail(accountantDTO.getEmail());

        Accountant savedAccountant = accountantRepository.save(accountant);
        return convertToDTO(savedAccountant);
    }

    @Override
    public List<AccountantDTO> getAllAccountants() {
        return accountantRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AccountantDTO> getAccountantById(Long id) {
        return accountantRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public AccountantDTO updateAccountant(Long id, AccountantDTO accountantDTO, Long companyId) {
        Accountant accountant = accountantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accountant not found"));

        User user = accountant.getUser();
        user.setUsername(accountantDTO.getUsername());
        user.setPassword(passwordEncoder.encode(accountantDTO.getPassword()));
        userRepository.save(user);

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        accountant.setCompany(company);

        accountant.setFirstName(accountantDTO.getFirstName());
        accountant.setLastName(accountantDTO.getLastName());
        accountant.setEmail(accountantDTO.getEmail());

        Accountant updatedAccountant = accountantRepository.save(accountant);
        return convertToDTO(updatedAccountant);
    }

    @Override
    public void deleteAccountant(Long id) {
        accountantRepository.deleteById(id);
    }

    private AccountantDTO convertToDTO(Accountant accountant) {
        AccountantDTO dto = new AccountantDTO();
        dto.setUsername(accountant.getUser().getUsername());
        dto.setFirstName(accountant.getFirstName());
        dto.setLastName(accountant.getLastName());
        dto.setEmail(accountant.getEmail());
        dto.setCompanyId(accountant.getCompany().getId());
        return dto;
    }
}
