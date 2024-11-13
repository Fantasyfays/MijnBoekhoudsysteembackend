package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dto.CompanyDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.CreateCompanyDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.UpdateCompanyDTO;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Company;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.ICompanyDal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final ICompanyDal companyDal;

    @Autowired
    public CompanyService(ICompanyDal companyDal) {
        this.companyDal = companyDal;
    }

    public CompanyDTO createCompany(CreateCompanyDTO createCompanyDTO) {
        Company company = convertToEntity(createCompanyDTO);
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

    public CompanyDTO updateCompany(Long id, UpdateCompanyDTO updateCompanyDTO) {
        Company company = companyDal.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));

        if (updateCompanyDTO.getName() != null) company.setName(updateCompanyDTO.getName());
        if (updateCompanyDTO.getAddress() != null) company.setAddress(updateCompanyDTO.getAddress());
        if (updateCompanyDTO.getEmail() != null) company.setEmail(updateCompanyDTO.getEmail());

        Company updatedCompany = companyDal.save(company);
        return convertToDTO(updatedCompany);
    }

    public void deleteCompany(Long id) {
        companyDal.deleteById(id);
    }

    private CompanyDTO convertToDTO(Company company) {
        return CompanyDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .address(company.getAddress())
                .email(company.getEmail())
                .build();
    }

    private Company convertToEntity(CreateCompanyDTO dto) {
        Company company = new Company();
        company.setName(dto.getName());
        company.setAddress(dto.getAddress());
        company.setEmail(dto.getEmail());
        return company;
    }
}
