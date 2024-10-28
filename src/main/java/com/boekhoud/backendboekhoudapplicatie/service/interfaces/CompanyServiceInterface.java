package com.boekhoud.backendboekhoudapplicatie.service.interfaces;

import com.boekhoud.backendboekhoudapplicatie.presentation.dto.CompanyDTO;
import java.util.List;
import java.util.Optional;

public interface CompanyServiceInterface {
    Optional<CompanyDTO> getCompanyById(Long id); // Retourneer een Optional<CompanyDTO>
    List<CompanyDTO> getAllCompanies();
    CompanyDTO createCompany(CompanyDTO companyDTO);
    CompanyDTO updateCompany(Long id, CompanyDTO companyDTO);
    void deleteCompany(Long id);
}
