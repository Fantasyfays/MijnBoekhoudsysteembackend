package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dal.repository.CompanyRepository;
import com.boekhoud.backendboekhoudapplicatie.model.Company;
import com.boekhoud.backendboekhoudapplicatie.presentation.dto.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        Company company = convertToEntity(companyDTO);
        Company savedCompany = companyRepository.save(company);
        return convertToDTO(savedCompany);
    }

    // Methode om een Company object om te zetten naar een DTO
    private CompanyDTO convertToDTO(Company company) {
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setAddress(company.getAddress());
        dto.setCity(company.getCity());
        dto.setZip(company.getZip());
        dto.setCountry(company.getCountry());
        dto.setPhone(company.getPhone());
        dto.setEmail(company.getEmail());
        return dto;
    }

    // Methode om een DTO om te zetten naar een Company object
    private Company convertToEntity(CompanyDTO dto) {
        Company company = new Company();
        company.setName(dto.getName());
        company.setAddress(dto.getAddress());
        company.setCity(dto.getCity());
        company.setZip(dto.getZip());
        company.setCountry(dto.getCountry());
        company.setPhone(dto.getPhone());
        company.setEmail(dto.getEmail());
        return company;
    }
}
