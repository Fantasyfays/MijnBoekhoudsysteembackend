package com.boekhoud.backendboekhoudapplicatie.presentation;

import com.boekhoud.backendboekhoudapplicatie.dto.CompanyDTO;
import com.boekhoud.backendboekhoudapplicatie.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/add")
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
        CompanyDTO savedCompany = companyService.createCompany(companyDTO);
        return ResponseEntity.ok(savedCompany);
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<CompanyDTO> companyDTOs = companyService.getAllCompanies();
        return ResponseEntity.ok(companyDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {
        CompanyDTO companyDTO = companyService.getCompanyById(id);
        return ResponseEntity.ok(companyDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Long id, @RequestBody CompanyDTO companyDTO) {
        CompanyDTO updatedCompany = companyService.updateCompany(id, companyDTO);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
