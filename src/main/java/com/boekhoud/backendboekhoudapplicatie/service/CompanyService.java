package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dto.CompanyDTO;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Company;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.ICompanyDal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final ICompanyDal ICompanyDal;

    @Autowired
    public CompanyService(ICompanyDal ICompanyDal) {
        this.ICompanyDal = ICompanyDal;
    }

    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        Company company = convertToEntity(companyDTO);
        Company savedCompany = ICompanyDal.save(company);
        return convertToDTO(savedCompany);
    }

    public List<CompanyDTO> getAllCompanies() {
        return ICompanyDal.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CompanyDTO getCompanyById(Long id) {
        Company company = ICompanyDal.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        return convertToDTO(company);
    }

    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {
        Company company = ICompanyDal.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));

        company.setName(companyDTO.getName());
        company.setAddress(companyDTO.getAddress());
        company.setEmail(companyDTO.getEmail());

        Company updatedCompany = ICompanyDal.save(company);
        return convertToDTO(updatedCompany);
    }

    public void deleteCompany(Long id) {
        ICompanyDal.deleteById(id);
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
