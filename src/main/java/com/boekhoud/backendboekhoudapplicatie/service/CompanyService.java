package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dto.CompanyDTO;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Company;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.CompanyDal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyDal companyDal;

    @Autowired
    public CompanyService(CompanyDal companyDal) {
        this.companyDal = companyDal;
    }

    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        Company company = convertToEntity(companyDTO);
        Company savedCompany = companyDal.save(company);
        return convertToDTO(savedCompany);
    }

    public List<CompanyDTO> getAllCompanies() {
        return companyDal.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CompanyDTO getCompanyById(Long id) {
        Company company = companyDal.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        return convertToDTO(company);
    }

    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {
        Company company = companyDal.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));

        company.setName(companyDTO.getName());
        company.setAddress(companyDTO.getAddress());
        company.setEmail(companyDTO.getEmail());

        Company updatedCompany = companyDal.save(company);
        return convertToDTO(updatedCompany);
    }

    public void deleteCompany(Long id) {
        companyDal.deleteById(id);
    }

    private CompanyDTO convertToDTO(Company company) {
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setAddress(company.getAddress());
        dto.setEmail(company.getEmail());
        return dto;
    }

    private Company convertToEntity(CompanyDTO dto) {
        Company company = new Company();
        company.setName(dto.getName());
        company.setAddress(dto.getAddress());
        company.setEmail(dto.getEmail());
        return company;
    }
}
