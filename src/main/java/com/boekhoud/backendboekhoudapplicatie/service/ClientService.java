package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Client;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Company;
import com.boekhoud.backendboekhoudapplicatie.enums.RoleType;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.User;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.ClientRepository;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.CompanyRepository;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.UserRepository;
import com.boekhoud.backendboekhoudapplicatie.dto.ClientDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.CreateClientDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.UpdateClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientService(ClientRepository clientRepository, CompanyRepository companyRepository,
                         UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ClientDTO createClientWithinCompany(CreateClientDTO createClientDTO, Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with ID: " + companyId));

        User user = new User();
        user.setUsername(createClientDTO.getUsername());
        user.setPassword(passwordEncoder.encode(createClientDTO.getPassword()));
        user.setRole(RoleType.CLIENT);
        User savedUser = userRepository.save(user);

        Client client = Client.builder()
                .name(createClientDTO.getName())
                .address(createClientDTO.getAddress())
                .kvkNumber(createClientDTO.getKvkNumber())
                .taxNumber(createClientDTO.getTaxNumber())
                .bankAccountNumber(createClientDTO.getBankAccountNumber())
                .email(createClientDTO.getEmail())
                .phoneNumber(createClientDTO.getPhoneNumber())
                .company(company)
                .user(savedUser)
                .build();

        Client savedClient = clientRepository.save(client);
        return convertToDTO(savedClient);
    }

    public ClientDTO updateClient(Long clientId, UpdateClientDTO updateClientDTO) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found with ID: " + clientId));

        client.setName(updateClientDTO.getName());
        client.setAddress(updateClientDTO.getAddress());
        client.setBankAccountNumber(updateClientDTO.getBankAccountNumber());
        client.setEmail(updateClientDTO.getEmail());
        client.setPhoneNumber(updateClientDTO.getPhoneNumber());

        Client updatedClient = clientRepository.save(client);
        return convertToDTO(updatedClient);
    }

    public List<ClientDTO> getAllClientsByCompanyId(Long companyId) {
        return clientRepository.findByCompanyId(companyId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ClientDTO convertToDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .address(client.getAddress())
                .kvkNumber(client.getKvkNumber())
                .taxNumber(client.getTaxNumber())
                .bankAccountNumber(client.getBankAccountNumber())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }
}
