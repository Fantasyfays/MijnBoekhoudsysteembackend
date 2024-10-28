package com.boekhoud.backendboekhoudapplicatie.service.implementatie;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Company;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.CompanyRepository;
import com.boekhoud.backendboekhoudapplicatie.presentation.dto.CompanyDTO;
import com.boekhoud.backendboekhoudapplicatie.service.interfaces.CompanyServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService implements CompanyServiceInterface {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setAddress(companyDTO.getAddress());
        company.setPhoneNumber(companyDTO.getPhoneNumber());

        Company savedCompany = companyRepository.save(company);
        return convertToDTO(savedCompany);
    }

    @Override
    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CompanyDTO> getCompanyById(Long id) {
        return companyRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.setName(companyDTO.getName());
        company.setAddress(companyDTO.getAddress());
        company.setPhoneNumber(companyDTO.getPhoneNumber());

        Company updatedCompany = companyRepository.save(company);
        return convertToDTO(updatedCompany);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    private CompanyDTO convertToDTO(Company company) {
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setAddress(company.getAddress());
        dto.setPhoneNumber(company.getPhoneNumber());
        return dto;
    }
}
