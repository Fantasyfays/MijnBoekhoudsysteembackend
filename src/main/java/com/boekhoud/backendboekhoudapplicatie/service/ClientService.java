package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Client;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Company;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.User;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.ClientRepository;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.CompanyRepository;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.UserRepository;
import com.boekhoud.backendboekhoudapplicatie.dto.ClientDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.CreateClientDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.UpdateClientDTO;
import com.boekhoud.backendboekhoudapplicatie.enums.RoleType;
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

        // Maak een nieuwe gebruiker aan voor de client
        User user = new User();
        user.setUsername(createClientDTO.getUsername());
        user.setPassword(passwordEncoder.encode(createClientDTO.getPassword()));
        user.setRole(RoleType.CLIENT);
        User savedUser = userRepository.save(user);

        // Maak een nieuwe client aan
        Client client = Client.builder()
                .companyName(createClientDTO.getCompanyName())
                .address(createClientDTO.getAddress())
                .zipCode(createClientDTO.getZipCode())
                .city(createClientDTO.getCity())
                .country(createClientDTO.getCountry())
                .kvkNumber(createClientDTO.getKvkNumber())
                .taxNumber(createClientDTO.getTaxNumber())
                .bank(createClientDTO.getBank())
                .swiftCode(createClientDTO.getSwiftCode())
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

        // Update velden van de client
        client.setCompanyName(updateClientDTO.getCompanyName());
        client.setAddress(updateClientDTO.getAddress());
        client.setZipCode(updateClientDTO.getZipCode());
        client.setCity(updateClientDTO.getCity());
        client.setCountry(updateClientDTO.getCountry());
        client.setBank(updateClientDTO.getBank());
        client.setSwiftCode(updateClientDTO.getSwiftCode());
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
                .companyName(client.getCompanyName())
                .address(client.getAddress())
                .zipCode(client.getZipCode())
                .city(client.getCity())
                .country(client.getCountry())
                .kvkNumber(client.getKvkNumber())
                .taxNumber(client.getTaxNumber())
                .bank(client.getBank())
                .swiftCode(client.getSwiftCode())
                .bankAccountNumber(client.getBankAccountNumber())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }
}
